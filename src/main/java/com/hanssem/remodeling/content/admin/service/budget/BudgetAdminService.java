package com.hanssem.remodeling.content.admin.service.budget;

import static java.util.stream.Collectors.groupingBy;

import com.hanssem.remodeling.content.admin.controller.budget.response.SimpleEstimateResponse;
import com.hanssem.remodeling.content.admin.repository.budget.BudgetEstimateAdminRepository;
import com.hanssem.remodeling.content.admin.repository.budget.BudgetPtypAdminRepository;
import com.hanssem.remodeling.content.admin.repository.budget.BudgetStyleAdminRepository;
import com.hanssem.remodeling.content.admin.repository.budget.BudgetStyleMaterialAdminRepository;
import com.hanssem.remodeling.content.admin.service.budget.dto.BasicCommonConstructionExcelDto;
import com.hanssem.remodeling.content.admin.service.budget.dto.BasicCommonConstructionExcelDto.BasicCommonConstructionLayoutCellEnum;
import com.hanssem.remodeling.content.admin.service.budget.dto.BudgetStyleExcelDto;
import com.hanssem.remodeling.content.admin.service.budget.dto.BudgetStyleExcelDto.LayoutCellEnum;
import com.hanssem.remodeling.content.admin.service.budget.dto.StyleImageDto;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import com.hanssem.remodeling.content.common.util.S3Uploader;
import com.hanssem.remodeling.content.constant.ConstructionFieldType;
import com.hanssem.remodeling.content.constant.ConstructionType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.constant.SpaceDetailType;
import com.hanssem.remodeling.content.constant.SpaceType;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetEstimate;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetPtyp;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyle;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyleMaterial;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetAdminService {

    private final BudgetStyleAdminRepository budgetStyleAdminRepository;
    private final BudgetStyleMaterialAdminRepository budgetStyleMaterialAdminRepository;
    private final BudgetPtypAdminRepository budgetPtypAdminRepository;
    private final BudgetEstimateAdminRepository budgetEstimateAdminRepository;
    private final S3Uploader s3Uploader;

    public boolean styleUpload(MultipartFile excel) {
        try {
            // ?????? ????????? ????????? ?????? ??????
            List<BudgetStyle> existingBudgetStyleList = budgetStyleAdminRepository.findAll();

            // ?????? ????????? ??????(????????? ??????????????????) ????????? ???????????? ??????
            final List<BasicCommonConstructionExcelDto> basicCommonConstructionExcelDtoList = convertBasicCommonExcelToDto(
                    excel.getInputStream());
            // ?????? ????????? ??????(????????? ????????? ??????,????????????) ????????? ???????????? ??????
            final List<BudgetStyleExcelDto> budgetStyleExcelDtoList = convertStyleExcelToDto(
                    excel.getInputStream());
            //????????? ??????????????????
            basicCommonStyleInsert(basicCommonConstructionExcelDtoList);
            //????????? ????????????, ????????????
            List<StyleImageDto> styleImageDtoList = styleDataInsert(budgetStyleExcelDtoList);

            //?????? ????????? ??????????????????
            existingStyleHide(existingBudgetStyleList);

            //????????? s3 ????????? ?????????
            styleImageUpload(styleImageDtoList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Transactional
    public void basicCommonStyleInsert(
            List<BasicCommonConstructionExcelDto> basicCommonConstructionExcelDtoList) {
        Map<Integer, List<BasicCommonConstructionExcelDto>> tempBasicCommonConstructionGroupData = basicCommonConstructionExcelDtoList.stream()
                .sorted(Comparator.comparing(BasicCommonConstructionExcelDto::getPtypValue))
                .collect(groupingBy(BasicCommonConstructionExcelDto::getPtypValue,
                        LinkedHashMap::new, Collectors.toList()));

        if (tempBasicCommonConstructionGroupData.containsKey(0)) {
            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }
        tempBasicCommonConstructionGroupData.forEach((strKey, strValue) -> {
            BudgetPtyp budgetPtyp = budgetPtypAdminRepository.findByPtypValue(strKey);
            BudgetStyle budgetStyle = BudgetStyle.builder().
                    budgetPtypMId(budgetPtyp.getId())
                    .spaceType(SpaceType.COMMON)
                    .useYn("Y")
                    .displaySeq(1)
                    .build();
            long budgetBasicCommonStyleMId = budgetStyleAdminRepository.save(budgetStyle)
                    .getId();

            List<BudgetStyleMaterial> budgetStyleMaterialList = new ArrayList<>();
            strValue.forEach((val) -> {
                BudgetStyleMaterial build = BudgetStyleMaterial.builder()
                        .budgetStyleMId(budgetBasicCommonStyleMId)
                        .constructionType(ConstructionType.BASIC_COMMON)
                        .constructionFieldType(val.getConstructionFieldType())
                        .materialName(val.getMaterialName())
                        .materialAmount(val.getMaterialAmount())
                        .constructionAmount(val.getConstructionAmount())
                        .totalAmount(val.getTotalAmount())
                        .build();
                budgetStyleMaterialList.add(build);
            });
            budgetStyleMaterialAdminRepository.saveAllAndFlush(budgetStyleMaterialList);
        });
    }

    @Transactional
    public List<StyleImageDto> styleDataInsert(List<BudgetStyleExcelDto> budgetStyleExcelDtoList) {
        int tempPtyp = 0;
        String tempStyleName = null , tempStyleTitle = null, tempMainImage = null, tempSubImage = null;
        SpaceType tempSpaceType = null;
        SpaceDetailType tempSpaceDetailType = null;

        List<StyleImageDto> styleImageDtoList = new ArrayList<>();
        List<BudgetStyleMaterial> budgetStyleMaterialList = new ArrayList<>();
        long budgetStyleMId = 0L;

        for (BudgetStyleExcelDto row : budgetStyleExcelDtoList) {
            //????????? ,?????????, ?????????, ?????????????????? ?????? ????????? ?????? ???????????? ??????
            if ((tempPtyp == row.getPtypValue()) && (tempStyleName == row.getStyleName()) && (
                    tempSpaceType == row.getSpaceType()) && (tempSpaceDetailType
                    == row.getSpaceDetailType())) {
                budgetStyleMId = budgetStyleMId;
            } else {
                tempPtyp = row.getPtypValue();
                tempStyleName = (row.getStyleName() != null) ? row.getStyleName() : tempStyleName;
                tempSpaceType = (row.getSpaceType() != null) ? row.getSpaceType() : null;
                tempSpaceDetailType =
                        (row.getSpaceDetailType() != null) ? row.getSpaceDetailType() : null;
                tempStyleTitle = (row.getStyleTitle() != null) ? row.getStyleTitle() : tempStyleTitle;
                tempMainImage =
                        (row.getStyleMainImagePathValue() != null) ? row.getStyleMainImagePathValue()
                                : tempMainImage;
                tempSubImage =
                        (row.getStyleSubImagePathValue() != null) ? row.getStyleSubImagePathValue()
                                : tempSubImage;
                BudgetPtyp budgetPtyp = budgetPtypAdminRepository.findByPtypValue(
                        row.getPtypValue());

                int displaySeq = (row.getSpaceDetailType() != null) ? StyleDisplaySeqToDetail(
                        row.getSpaceDetailType()) : StyleDisplaySeq(row.getSpaceType());

                BudgetStyle budgetStyle = BudgetStyle.builder().
                        budgetPtypMId(budgetPtyp.getId())
                        .spaceType(row.getSpaceType())
                        .spaceDetailType(row.getSpaceDetailType())
                        .styleName(row.getStyleName())
                        .styleTitle(row.getStyleTitle())
                        .useYn("N")
                        .displaySeq(displaySeq)
                        .build();
                budgetStyleMId = budgetStyleAdminRepository.save(budgetStyle).getId();

                StyleImageDto styleImageDtoBuild = StyleImageDto.builder()
                        .styleId(budgetStyleMId)
                        .styleMainImagePathValue(row.getStyleMainImagePathValue())
                        .styleSubImagePathValue(row.getStyleSubImagePathValue())
                        .build();
                styleImageDtoList.add(styleImageDtoBuild);
            }
            //?????? ?????? ?????????
            BudgetStyleMaterial materialBuild = BudgetStyleMaterial.builder()
                    .budgetStyleMId(budgetStyleMId)
                    .constructionType(row.getConstructionType())
                    .constructionFieldType(row.getConstructionFieldType())
                    .materialName(row.getMaterialName())
                    .materialAmount(row.getMaterialAmount())
                    .constructionAmount(row.getConstructionAmount())
                    .totalAmount(row.getTotalAmount())
                    .build();
            budgetStyleMaterialList.add(materialBuild);
        }
        budgetStyleMaterialAdminRepository.saveAllAndFlush(budgetStyleMaterialList);

        return styleImageDtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void existingStyleHide(List<BudgetStyle> budgetStyleList) {
        budgetStyleList.forEach(entity -> entity.changeUseYn("N"));
        budgetStyleAdminRepository.saveAllAndFlush(budgetStyleList);
    }

    private int StyleDisplaySeq(SpaceType spaceType) {
        int displaySeq = 0;

        switch (spaceType) {
            case COMMON:
                displaySeq = 1;
                break;
            case LIVING_ROOM:
                displaySeq = 2;
                break;
            case KITCHEN:
                displaySeq = 3;
                break;
            case ENTRANCE:
                displaySeq = 4;
                break;
        }
        return displaySeq;
    }

    private int StyleDisplaySeqToDetail(SpaceDetailType spaceDetailType) {
        int displaySeq = 0;

        switch (spaceDetailType) {
            case PUBLIC_BATH:
                displaySeq = 5;
                break;
            case SMALL_BATH:
                displaySeq = 6;
                break;
            case BED_ROOM:
                displaySeq = 7;
                break;
            case STUDY_ROOM:
                displaySeq = 8;
                break;
            case CHILD_ROOM:
                displaySeq = 9;
                break;
            case DRESS_ROOM:
                displaySeq = 10;
                break;
            case UTILITY_ROOM:
                displaySeq = 11;
                break;
        }
        return displaySeq;
    }

    @Async
    public void styleImageUpload(List<StyleImageDto> styleImageDtoList) {
        for (StyleImageDto styleImageDto : styleImageDtoList) {
            String styleMainImagePath = null, styleSubImagePath = null;

            try {
                if ((styleImageDto.getStyleMainImagePathValue() != null)
                        && (!styleImageDto.getStyleMainImagePathValue().trim().isEmpty())) {
                    URL mainImageUrl = new URL(styleImageDto.getStyleMainImagePathValue());
                    styleMainImagePath = s3Uploader.putS3forBudgetStyleImageUpload(
                            mainImageUrl.openStream());
                }
                if ((styleImageDto.getStyleSubImagePathValue() != null)
                        && (!styleImageDto.getStyleSubImagePathValue().trim().isEmpty())) {
                    URL subImageUrl = new URL(styleImageDto.getStyleSubImagePathValue());
                    styleSubImagePath = s3Uploader.putS3forBudgetStyleImageUpload(
                            subImageUrl.openStream());
                }
                BudgetStyle budgetStyle = budgetStyleAdminRepository.findById(
                        styleImageDto.getStyleId()).orElseThrow(() -> new CustomException(ResponseCode.BUDGET_NOT_FOUND));

                budgetStyle.changeImagePath(styleMainImagePath, styleSubImagePath);
                budgetStyle.changeUseYn("Y");
                budgetStyleAdminRepository.save(budgetStyle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //????????? ?????????????????? ???????????? ????????? ??????
    private List<BasicCommonConstructionExcelDto> convertBasicCommonExcelToDto(
            final InputStream is) {
        final List<BasicCommonConstructionExcelDto> datas = new ArrayList<>();

        try {
            XSSFRow row;
            final XSSFWorkbook workbook = new XSSFWorkbook(is);
            // ??????????????? (????????? ?????????????????? ??????)
            final XSSFSheet dataSheet = workbook.getSheetAt(0);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            workbook.setForceFormulaRecalculation(true);

            for (int i = BasicCommonConstructionExcelDto.ADMIN_LAYOUT_EXCEL_START_ROWNUM;
                    i <= dataSheet.getPhysicalNumberOfRows(); i++) {
                row = dataSheet.getRow(i);

                if (row != null) {
                    try {
                        datas.add(BasicCommonConstructionExcelDto.builder()
                                .ptypValue((int) row.getCell(
                                                BasicCommonConstructionLayoutCellEnum.PTYP_VALUE.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .constructionFieldType(ConstructionFieldType.valueOfName(
                                        row.getCell(
                                                        BasicCommonConstructionLayoutCellEnum.CONSTRUCTION_FILED_TYPE.getCellNumber(),
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                                .getStringCellValue()))
                                .materialName(row.getCell(
                                                BasicCommonConstructionLayoutCellEnum.MATERIAL_NAME.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getStringCellValue())
                                .materialAmount((int) row.getCell(
                                                BasicCommonConstructionLayoutCellEnum.MATERIAL_AMOUNT.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .constructionAmount((int) row.getCell(
                                                BasicCommonConstructionLayoutCellEnum.CONSTRUCTION_AMOUNT.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .totalAmount((int) row.getCell(
                                                BasicCommonConstructionLayoutCellEnum.TOTAL_AMOUNT.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    //?????? ????????? ????????? ??????
    private List<BudgetStyleExcelDto> convertStyleExcelToDto(final InputStream is) {
        final List<BudgetStyleExcelDto> datas = new ArrayList<>();
        int sheetCount = 1;

        try {
            XSSFRow row;
            final XSSFWorkbook workbook = new XSSFWorkbook(is);

            final XSSFSheet dataSheet = workbook.getSheetAt(sheetCount);

            for (int i = BudgetStyleExcelDto.ADMIN_LAYOUT_EXCEL_START_ROWNUM;
                    i < dataSheet.getPhysicalNumberOfRows(); i++) {
                row = dataSheet.getRow(i);

                if (row != null) {
                    try {
                        datas.add(BudgetStyleExcelDto.builder()
                                .ptypValue(
                                        (int) row.getCell(LayoutCellEnum.PTYP_VALUE.getCellNumber(),
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                                .getNumericCellValue())
                                .styleName(
                                        row.getCell(LayoutCellEnum.STYLE_NAME.getCellNumber(),
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                                .getStringCellValue())
                                .spaceType(SpaceType.valueOfName(row.getCell(
                                                LayoutCellEnum.SPACE_TYPE.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getStringCellValue()))
                                .spaceDetailType(SpaceDetailType.valueOfName(row.getCell(
                                                LayoutCellEnum.SPACE_DETAIL_TYPE.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getStringCellValue()))
                                .constructionType(ConstructionType.valueOfName(row.getCell(
                                                LayoutCellEnum.CONSTRUCTION_TYPE.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getStringCellValue()))
                                .constructionFieldType(
                                        ConstructionFieldType.valueOfName(row.getCell(
                                                        LayoutCellEnum.CONSTRUCTION_FILED_TYPE.getCellNumber(),
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                                .getStringCellValue()))
                                .materialName(
                                        row.getCell(
                                                        LayoutCellEnum.MATERIAL_NAME.getCellNumber(),
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                                .getStringCellValue())
                                .materialAmount((int) row.getCell(
                                                LayoutCellEnum.MATERIAL_AMOUNT.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .constructionAmount((int) row.getCell(
                                                LayoutCellEnum.CONSTRUCTION_AMOUNT.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .totalAmount((int) row.getCell(
                                                LayoutCellEnum.TOTAL_AMOUNT.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getNumericCellValue())
                                .styleTitle(
                                        row.getCell(LayoutCellEnum.STYLE_TITLE.getCellNumber(),
                                                        MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                                .getStringCellValue())
                                .styleMainImagePathValue(row.getCell(
                                                LayoutCellEnum.STYLE_MAIN_IMAGE_PATH_VALUE.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getStringCellValue())
                                .styleSubImagePathValue(row.getCell(
                                                LayoutCellEnum.STYLE_SUB_IMAGE_PATH_VALUE.getCellNumber(),
                                                MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                        .getStringCellValue())
                                .build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    @Transactional(readOnly = true)
    public SimpleEstimateResponse getEstimateSimple(long estimateMId) {
        BudgetEstimate budgetEstimate = budgetEstimateAdminRepository.findById(
                        estimateMId);

        if(budgetEstimate != null) {
            //????????? ????????? ?????????[???????????????]
            String imagePath = budgetEstimate.getBudgetEstimateInfoList().stream()
                    .filter(info -> (info.getBudgetStyle().getDisplaySeq() == 2))
                    .map(info -> info.getBudgetStyle().getStyleMainImagePath())
                    .findFirst().orElse(null);

            SimpleEstimateResponse simpleEstimateResponse = SimpleEstimateResponse.builder()
                    .estimateMId(budgetEstimate.getId())
                    .userId(budgetEstimate.getUserId())
                    .ptypValue(budgetEstimate.getPtypValue())
                    .roomCount(budgetEstimate.getRoomCount())
                    .bathCount(budgetEstimate.getBathCount())
                    .image(GlobalConstants.makeCdnUrl(imagePath))
                    .totalEstimateAmount(budgetEstimate.getTotalEstimateAmount())
                    .consultYn(budgetEstimate.getConsultYn())
                    .build();

            return simpleEstimateResponse;
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void completeEstimateConsult(long estimateMId) {
        BudgetEstimate budgetEstimate = budgetEstimateAdminRepository.findById(estimateMId);

        if(budgetEstimate != null) {
            if (budgetEstimate.getConsultYn().equals("N")) {
                budgetEstimate.changeConsultYn("Y");
                budgetEstimateAdminRepository.save(budgetEstimate);
            } else {
                throw new CustomException(ResponseCode.BUDGET_ALREADY_CONSULT_STATE_CHANGE);
            }
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }
}
