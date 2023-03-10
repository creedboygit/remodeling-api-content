package com.hanssem.remodeling.content.api.service.budget;

import static java.util.stream.Collectors.groupingBy;

import com.hanssem.remodeling.content.api.controller.budget.request.SaveEstimateRequest;
import com.hanssem.remodeling.content.api.controller.budget.request.StyleEstimateRequest;
import com.hanssem.remodeling.content.api.controller.budget.response.PtypResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.PtypStyleResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.SaveEstimateResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.StyleEstimateResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.StyleEstimateResponse.SpaceImage;
import com.hanssem.remodeling.content.api.controller.budget.response.StyleMaterialResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.StyleResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.UsersEstimateResponse;
import com.hanssem.remodeling.content.api.repository.budget.BudgetEstimateInfoQueryRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetEstimateInfoRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetEstimateRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetPtypOptionRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetPtypRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetStyleMaterialRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetStyleQueryRepository;
import com.hanssem.remodeling.content.api.repository.budget.BudgetStyleRepository;
import com.hanssem.remodeling.content.api.service.budget.vo.BudgetStyleVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import com.hanssem.remodeling.content.constant.ConstructionFieldType;
import com.hanssem.remodeling.content.constant.ConstructionType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.constant.SpaceAllType;
import com.hanssem.remodeling.content.constant.SpaceDetailType;
import com.hanssem.remodeling.content.constant.SpaceType;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetEstimate;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetEstimateInfo;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetPtyp;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetPtypOption;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyle;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyleMaterial;
import com.hanssem.remodeling.content.domain.budget.mapper.BudgetMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetPtypRepository budgetPtypRepository;
    private final BudgetPtypOptionRepository budgetPtypOptionRepository;
    private final BudgetStyleQueryRepository budgetStyleQueryRepository;
    private final BudgetStyleRepository budgetStyleRepository;
    private final BudgetStyleMaterialRepository budgetStyleMaterialRepository;
    private final BudgetEstimateRepository budgetEstimateRepository;
    private final BudgetEstimateInfoRepository budgetEstimateInfoRepository;
    private final BudgetEstimateInfoQueryRepository budgetEstimateInfoQueryRepository;

    //?????? ????????? ??????(???????????? ???,?????? ?????? ??????)
    @Transactional(readOnly = true)
    public PtypResponse getPtypList() {
        //???????????? ?????? ?????? ??????
        List<BudgetPtyp> budgetPtypList = budgetPtypRepository.findAll();

        List<PtypResponse.PtypData> ptypData = new ArrayList<>();

        for (BudgetPtyp ptyp : budgetPtypList) {
            //????????????
            List<PtypResponse.PtypRoomOption> roomOption = new ArrayList<>();
            List<PtypResponse.PtypBathOption> bathOption = new ArrayList<>();

            //???????????? ??????
            for (BudgetPtypOption ptypOption : ptyp.getBudgetPtypOptionList()) {
                if (ptypOption.getBudgetSpaceType().equals(SpaceType.ROOM)) {
                    PtypResponse.PtypRoomOption roomBuild = PtypResponse.PtypRoomOption.builder()
                            .optionId(ptypOption.getId())
                            .count(ptypOption.getCount())
                            .build();
                    roomOption.add(roomBuild);
                } else if (ptypOption.getBudgetSpaceType().equals(SpaceType.BATH)) {
                    PtypResponse.PtypBathOption bathBuild = PtypResponse.PtypBathOption.builder()
                            .optionId(ptypOption.getId())
                            .count(ptypOption.getCount())
                            .build();
                    bathOption.add(bathBuild);
                }
            }
            PtypResponse.PtypData ptypBuild = PtypResponse.PtypData.builder()
                    .ptypMId(ptyp.getId())
                    .room(roomOption)
                    .bath(bathOption)
                    .ptypValue(ptyp.getPtypValue())
                    .build();
            ptypData.add(ptypBuild);
        }
        PtypResponse ptypResponseResult = PtypResponse.builder().ptypData(ptypData).build();

        return ptypResponseResult;
    }

    @Transactional(readOnly = true)
    public List<UsersEstimateResponse> getUserEstimate(Long userId, Long AuthUserId) {
        if (userId.equals(AuthUserId)) {
            //???????????? ??????????????? ?????? ??????
            List<BudgetEstimate> estimateAllData = budgetEstimateRepository.findByUserIdOrderByIdDesc(
                    userId);

            //?????? ?????? ??????
            List<UsersEstimateResponse> estimate = estimateAllData.stream().map(entity -> {
                //?????????????????? ?????? ????????? ??????????????? ????????? ??????
                List<String> images = new ArrayList<>();

                //????????? ????????? ?????????[??????]
                String tempImagePath1 = entity.getBudgetEstimateInfoList().stream()
                        .filter(info -> (info.getBudgetStyle().getDisplaySeq() == 2))
                        .map(info -> info.getBudgetStyle().getStyleMainImagePath())
                        .findFirst().orElse(null);
                String imagePath1 = GlobalConstants.makeCdnUrl(tempImagePath1);
                images.add(imagePath1);

                //????????? ????????? ?????????[??????]
                String tempImagePath2 = entity.getBudgetEstimateInfoList().stream()
                        .filter(info -> (info.getBudgetStyle().getDisplaySeq() == 3))
                        .map(info -> info.getBudgetStyle().getStyleMainImagePath())
                        .findFirst().orElse(null);
                String imagePath2 = GlobalConstants.makeCdnUrl(tempImagePath2);
                images.add(imagePath2);

                //????????? ????????? ?????????[??????]
                String tempImagePath3 = entity.getBudgetEstimateInfoList().stream()
                        .filter(info -> (info.getBudgetStyle().getDisplaySeq() == 4))
                        .map(info -> info.getBudgetStyle().getStyleMainImagePath())
                        .findFirst().orElse(null);
                String imagePath3 = GlobalConstants.makeCdnUrl(tempImagePath3);
                images.add(imagePath3);

                return UsersEstimateResponse.builder()
                        .estimateMId(entity.getId())
                        .userId(entity.getUserId())
                        .ptypValue(entity.getPtypValue())
                        .roomCount(entity.getRoomCount())
                        .bathCount(entity.getBathCount())
                        .totalEstimateAmount(entity.getTotalEstimateAmount())
                        .consultYn(entity.getConsultYn())
                        .images(images)
                        .build();
            }).collect(Collectors.toList());

            //?????? ??????
            return estimate;
        } else {
            throw new CustomException(ResponseCode.BUDGET_INCONSISTENCY_USER_ID);
        }
    }

    @Transactional(readOnly = true)
    public StyleEstimateResponse getEstimate(long estimateMId, Long AuthUserId) {
        //???????????? ?????? ??????
        List<BudgetEstimateInfo> tempEstimateInfoData = budgetEstimateInfoQueryRepository.findByBudgetEstimateMIdAndUserId(
                estimateMId, AuthUserId);

        if (!tempEstimateInfoData.isEmpty()) {
            //?????? ????????? ?????????
            List<StyleEstimateResponse.SpaceImage> spaceImageList = SpaceImage(
                    tempEstimateInfoData);

            //????????? ?????? ?????? ?????? ???????????? ??????
            List<Long> budgetStyleMIds = tempEstimateInfoData.stream()
                    .map(BudgetEstimateInfo::getBudgetStyleMId).toList();
            List<BudgetStyleMaterial> tempBudgetStyleMaterial = budgetStyleMaterialRepository.findByBudgetStyleMIdIn(
                    budgetStyleMIds);

            //???????????? ?????? ?????? ????????????
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> commonMaterialGroupData = tempBudgetStyleMaterial.stream()
                    .filter(entity -> (entity.getConstructionType().equals(ConstructionType.COMMON)
                            || entity.getConstructionType().equals(ConstructionType.BASIC_COMMON)))
                    .collect(groupingBy(BudgetStyleMaterial::getConstructionFieldType,
                            LinkedHashMap::new, Collectors.toList()));

            //?????? ????????? ????????? ?????? ?????? ?????? ??????
            List<BudgetStyle> tempBudgetStyleList = new ArrayList<>();
            final long[] prevStyleMId = {0L};
            tempBudgetStyleMaterial.stream()
                    .filter(entity -> (entity.getConstructionType().equals(ConstructionType.SPACE)))
                    .forEach(entity -> {
                        if(!entity.getBudgetStyleMId().equals(prevStyleMId[0])) {
                            tempBudgetStyleList.add(entity.getBudgetStyle());
                            prevStyleMId[0] = entity.getBudgetStyleMId();
                        }
                    });

            //?????????, ????????????????????? ????????? ?????? ?????? ??????
            List<BudgetStyleVo> tempBudgetStyle = tempBudgetStyleList.stream().map(entity -> {
                return BudgetStyleVo.builder()
                        .budgetPtypMId(entity.getBudgetPtypMId())
                        .spaceType((entity.getSpaceDetailType() != null) ? String.valueOf(
                                entity.getSpaceDetailType())
                                : String.valueOf(entity.getSpaceType()))
                        .styleName(entity.getStyleName())
                        .styleTitle(entity.getStyleTitle())
                        .styleMainImagePath(entity.getStyleMainImagePath())
                        .styleSubImagePath(entity.getStyleSubImagePath())
                        .displaySeq(entity.getDisplaySeq())
                        .useYn(entity.getUseYn())
                        .budgetStyleMaterialList(entity.getBudgetStyleMaterialList())
                        .build();
            }).collect(Collectors.toList());

            Map<String, List<BudgetStyleVo>> tempSpaceMaterialGroupData = tempBudgetStyle.stream()
                    .sorted(Comparator.comparing(BudgetStyleVo::getDisplaySeq))
                    .collect(
                            groupingBy(BudgetStyleVo::getSpaceType, LinkedHashMap::new,
                                    Collectors.toList()));

            Map<String, List<BudgetStyleMaterial>> spaceMaterialGroupData = new LinkedHashMap<>();


            tempSpaceMaterialGroupData.forEach((strKey, strValue) -> {
                List<BudgetStyleMaterial> tempSpaceMaterialList = new ArrayList<>();
                strValue.forEach((value) -> {
                    value.getBudgetStyleMaterialList().stream()
                            .filter(material -> (material.getConstructionType().equals(ConstructionType.SPACE)))
                            .forEach(material -> {
                                //???????????? ?????? ???????????? PUT
                                tempSpaceMaterialList.add(material);
                            });
                });
                spaceMaterialGroupData.put(strKey,tempSpaceMaterialList);
            });
            //???????????? ??????
            StyleEstimateResponse.CommonConstruction commonConstructions = CommonConstruction(
                    commonMaterialGroupData);
            //???????????? ??????
            StyleEstimateResponse.SpaceConstruction spaceConstructions = SpaceConstruction(
                    spaceMaterialGroupData);

            //?????? ?????? ??????(?????? , ??????)
            int totalCommonConstructionAmount = commonConstructions.getTotalCommonConstructionAmount();
            int totalSpaceConstructionAmount = spaceConstructions.getTotalSpaceConstructionAmount();

            //?????? ??????( ??????????????? + ????????????)
            StyleEstimateResponse styleEstimateResponse = StyleEstimateResponse.builder()
                    .spaceImagesList(spaceImageList)
                    .totalConstructionAmount(
                            totalCommonConstructionAmount + totalSpaceConstructionAmount)
                    .commonConstruction(commonConstructions)
                    .spaceConstruction(spaceConstructions)
                    .build();

            //??????????????? ??????
            return styleEstimateResponse;
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    private long saveEstimateMaster(SaveEstimateRequest request, Long AuthUserId) {
        List<Long> styleIdList = request.getEstimateInfoList().stream().map(x -> x.getStyleMId())
                .toList();

        List<BudgetStyle> budgetStyleList = budgetStyleRepository.findByIdIn(
                styleIdList);

        if (styleIdList.size() == budgetStyleList.size()) {
            BudgetEstimate budgetEstimate = BudgetEstimate.builder()
                    .userId(AuthUserId)
                    .ptypValue(request.getPtypValue())
                    .roomCount(request.getRoomCount())
                    .bathCount(request.getBathCount())
                    .totalEstimateAmount(request.getTotalEstimateAmount())
                    .build();
            long estimateMId = budgetEstimateRepository.save(budgetEstimate).getId();

            return estimateMId;
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public SaveEstimateResponse saveEstimate(SaveEstimateRequest request, Long AuthUserId) {
        long estimateMId = saveEstimateMaster(request, AuthUserId);

        request.getEstimateInfoList().stream().forEach(info -> {
            BudgetEstimateInfo budgetEstimateInfo = BudgetEstimateInfo.builder()
                    .budgetEstimateMId(estimateMId)
                    .budgetStyleMId(info.getStyleMId())
                    .build();
            budgetEstimateInfoRepository.save(budgetEstimateInfo);
        });

        return SaveEstimateResponse.of(estimateMId, AuthUserId);
    }

    @Transactional(rollbackFor = Exception.class)
    public SaveEstimateResponse saveEstimateNoAuth(SaveEstimateRequest request) {
        long estimateMId = saveEstimateMaster(request, 0L);

        request.getEstimateInfoList().stream().forEach(info -> {
            BudgetEstimateInfo budgetEstimateInfo = BudgetEstimateInfo.builder()
                    .budgetEstimateMId(estimateMId)
                    .budgetStyleMId(info.getStyleMId())
                    .build();
            budgetEstimateInfoRepository.save(budgetEstimateInfo);
        });

        return SaveEstimateResponse.of(estimateMId, 0L);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeEstimate(long estimateMId, long AuthUserId) {
        BudgetEstimate budgetEstimate = budgetEstimateRepository.findByIdAndUserId(
                estimateMId, AuthUserId);

        if (budgetEstimate != null) {
            budgetEstimateRepository.delete(budgetEstimate);
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    //????????? ??????
    @Transactional(readOnly = true)
    public List<PtypStyleResponse> getPtypStyle(long ptypMId, SpaceType spaceType,
            SpaceDetailType spaceDetailType) {
        List<BudgetStyle> tempStyleData = new ArrayList<>();

        if (spaceType == null) {
            if (spaceDetailType == null) {
                tempStyleData = budgetStyleQueryRepository.findByPtypId(
                        ptypMId);
            }
        } else {
            if (spaceDetailType == null) {
                tempStyleData = budgetStyleQueryRepository.findByPtypIdAndSpaceType(ptypMId,
                        spaceType);
            } else {
                tempStyleData = budgetStyleQueryRepository.findByPtypIdAndSpaceTypeAndSpaceDetailType(
                        ptypMId, spaceType, spaceDetailType);
            }
        }

        if (!tempStyleData.isEmpty()) {
            Map<SpaceType, List<BudgetStyle>> groupbyStyleData =
                    tempStyleData.stream()
                            .collect(groupingBy(BudgetStyle::getSpaceType, LinkedHashMap::new,
                                    Collectors.toList()));

            List<PtypStyleResponse> styleResult = new ArrayList<>();

            groupbyStyleData
                    .forEach((strKey, strValue) -> {
                        List<PtypStyleResponse.StyleList> styleLists = new ArrayList<>();
                        strValue.forEach((value) -> {
                            List<StyleMaterialResponse> styleMaterialList = BudgetMapper.INSTANCE.toStyleMaterialResponse(
                                    value.getBudgetStyleMaterialList());
                            //????????? ???????????? ?????? ????????? ??????
                            int totalAmount = styleMaterialList.stream()
                                    .mapToInt(StyleMaterialResponse::getTotalAmount).sum();

                            styleLists.add(
                                    PtypStyleResponse.StyleList.builder()
                                            .displaySeq(value.getDisplaySeq())
                                            .styleMId(value.getId())
                                            .spaceDetailName((value.getSpaceDetailType() != null)
                                                    ? value.getSpaceDetailType().getName() : null)
                                            .spaceDetailType((value.getSpaceDetailType() != null)
                                                    ? value.getSpaceDetailType().getCode() : null)
                                            .styleName(value.getStyleName())
                                            .styleTitle(value.getStyleTitle())
                                            .styleMainImageUrl(GlobalConstants.makeCdnUrl(
                                                    value.getStyleMainImagePath()))
                                            .styleSubImageUrl(GlobalConstants.makeCdnUrl(
                                                    value.getStyleSubImagePath()))
                                            .totalAmount(totalAmount)
                                            .build()
                            );
                        });
                        styleResult.add(PtypStyleResponse.builder().spaceName(strKey.getName())
                                .spaceType(strKey.getCode()).style(styleLists).build());
                    });

            return styleResult;
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    // ????????? ??? ??????(??????,??????) ??????
    @Transactional(readOnly = true)
    public StyleResponse getStyleMaterial(long styleMId) {
        BudgetStyle tempResult = budgetStyleRepository.findById(styleMId);

        if(tempResult != null) {
            List<StyleResponse.ConstrcutionProcess> commonConstructionProcess = new ArrayList<>();
            List<StyleResponse.ConstrcutionProcess> spaceConstructionProcess = new ArrayList<>();

            //???????????? ?????????
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> groupingStyleCommonData =
                    tempResult.getBudgetStyleMaterialList().stream()
                            .filter(entity -> (
                                    entity.getConstructionType().equals(ConstructionType.COMMON)
                                            || entity.getConstructionType()
                                            .equals(ConstructionType.BASIC_COMMON)))
                            .collect(groupingBy(BudgetStyleMaterial::getConstructionFieldType,
                                    LinkedHashMap::new,
                                    Collectors.toList()));
            //???????????? ?????????
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> groupingStyleSpaceData =
                    tempResult.getBudgetStyleMaterialList().stream()
                            .filter(entity -> (entity.getConstructionType()
                                    .equals(ConstructionType.SPACE)))
                            .collect(groupingBy(BudgetStyleMaterial::getConstructionFieldType,
                                    LinkedHashMap::new,
                                    Collectors.toList()));
            //?????? ??????
            final int[] totalCommonAmount = {0};

            groupingStyleCommonData.forEach((strKey, strValue) -> {
                List<StyleResponse.Material> commonMaterialLists = new ArrayList<>();

                int groupCommonAmount = strValue.stream()
                        .mapToInt(BudgetStyleMaterial::getTotalAmount).sum();
                totalCommonAmount[0] += groupCommonAmount;

                strValue.forEach((val) -> {
                    StyleResponse.Material commonMaterialBuild = StyleResponse.Material.builder()
                            .materialName(val.getMaterialName())
                            .build();
                    commonMaterialLists.add(commonMaterialBuild);
                });

                StyleResponse.ConstrcutionProcess constructionBuild1 = StyleResponse.ConstrcutionProcess.builder()
                        .constructionFieldType(strKey.getCode())
                        .constructionField(strKey.getName())
                        .totalAmount(groupCommonAmount)
                        .material(commonMaterialLists)
                        .build();
                commonConstructionProcess.add(constructionBuild1);
            });
            StyleResponse.CommonConstructionList commonConstructionList = StyleResponse.CommonConstructionList.builder()
                    .totalCommonAmount(totalCommonAmount[0])
                    .process(commonConstructionProcess)
                    .build();

            //?????? ??????
            final int[] totalSpaceAmount = {0};

            groupingStyleSpaceData.forEach((strKey, strValue) -> {
                List<StyleResponse.Material> spaceMaterialLists = new ArrayList<>();

                int groupSpaceAmount = strValue.stream()
                        .mapToInt(BudgetStyleMaterial::getTotalAmount).sum();
                totalSpaceAmount[0] += groupSpaceAmount;

                strValue.forEach((val) -> {
                    StyleResponse.Material spaceMaterialBuild = StyleResponse.Material.builder()
                            .materialName(val.getMaterialName())
                            .build();
                    spaceMaterialLists.add(spaceMaterialBuild);
                });

                StyleResponse.ConstrcutionProcess constructionBuild2 = StyleResponse.ConstrcutionProcess.builder()
                        .constructionFieldType(strKey.getCode())
                        .constructionField(strKey.getName())
                        .totalAmount(groupSpaceAmount)
                        .material(spaceMaterialLists)
                        .build();
                spaceConstructionProcess.add(constructionBuild2);
            });
            StyleResponse.SpaceConstructionList spaceConstructionList = StyleResponse.SpaceConstructionList.builder()
                    .totalSpaceAmount(totalSpaceAmount[0])
                    .process(spaceConstructionProcess)
                    .build();

            StyleResponse styleResponse = StyleResponse.builder()
                    .styleMId(tempResult.getId())
                    .spaceName(tempResult.getSpaceType().getName())
                    .spaceDetailName(
                            (tempResult.getSpaceDetailType() != null)
                                    ? tempResult.getSpaceDetailType()
                                    .getName() : null)
                    .styleTitle(tempResult.getStyleTitle())
                    .styleMainImageUrl(
                            GlobalConstants.makeCdnUrl(tempResult.getStyleMainImagePath()))
                    .styleSubImageUrl(GlobalConstants.makeCdnUrl(tempResult.getStyleSubImagePath()))
                    .commonConstruction(commonConstructionList)
                    .spaceConstruction(spaceConstructionList)
                    .build();
            return styleResponse;
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    @Transactional
    public StyleEstimateResponse getStyleEstimate(StyleEstimateRequest request) {
        List<Long> styleIdList = request.getEstimateInfoList().stream().map(x -> x.getStyleMId())
                .toList();

        List<BudgetStyle> budgetStyleList = budgetStyleRepository.findByIdInOrderByDisplaySeqAsc(
                styleIdList);

        if (!budgetStyleList.isEmpty()) {
            //?????? ????????? ?????????
            List<StyleEstimateResponse.SpaceImage> spaceImage = SpaceImageStyle(
                    budgetStyleList);

            //????????? ?????? ?????? ?????? ???????????? ??????
            List<BudgetStyleMaterial> tempBudgetStyleMaterial = budgetStyleMaterialRepository.findByBudgetStyleMIdIn(
                    styleIdList);

            //???????????? ?????? ?????? ????????????
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> commonMaterialGroupData = tempBudgetStyleMaterial.stream()
                    .filter(entity -> (
                            entity.getConstructionType().equals(ConstructionType.COMMON)
                                    || entity.getConstructionType()
                                    .equals(ConstructionType.BASIC_COMMON)))
                    .collect(groupingBy(BudgetStyleMaterial::getConstructionFieldType,
                            LinkedHashMap::new, Collectors.toList()));

            //?????? ????????? ????????? ?????? ?????? ?????? ??????
            List<BudgetStyle> tempBudgetStyleList = new ArrayList<>();
            final long[] prevStyleMId = {0L};

            tempBudgetStyleMaterial.stream()
                    .filter(entity -> (entity.getConstructionType()
                            .equals(ConstructionType.SPACE)))
                    .forEach(entity -> {
                        if(!entity.getBudgetStyleMId().equals(prevStyleMId[0])) {
                            tempBudgetStyleList.add(entity.getBudgetStyle());
                            prevStyleMId[0] = entity.getBudgetStyleMId();
                        }
                    });

            //?????????, ????????????????????? ????????? ?????? ?????? ??????
            List<BudgetStyleVo> tempBudgetStyle = tempBudgetStyleList.stream().map(entity -> {
                return BudgetStyleVo.builder()
                        .budgetPtypMId(entity.getBudgetPtypMId())
                        .spaceType((entity.getSpaceDetailType() != null) ? String.valueOf(
                                entity.getSpaceDetailType())
                                : String.valueOf(entity.getSpaceType()))
                        .styleName(entity.getStyleName())
                        .styleTitle(entity.getStyleTitle())
                        .styleMainImagePath(
                                GlobalConstants.makeCdnUrl(entity.getStyleMainImagePath()))
                        .styleSubImagePath(
                                GlobalConstants.makeCdnUrl(entity.getStyleMainImagePath()))
                        .displaySeq(entity.getDisplaySeq())
                        .useYn(entity.getUseYn())
                        .budgetStyleMaterialList(entity.getBudgetStyleMaterialList())
                        .build();
            }).collect(Collectors.toList());

            Map<String, List<BudgetStyleVo>> tempSpaceMaterialGroupData = tempBudgetStyle.stream()
                    .sorted(Comparator.comparing(BudgetStyleVo::getDisplaySeq))
                    .collect(
                            groupingBy(BudgetStyleVo::getSpaceType, LinkedHashMap::new,
                                    Collectors.toList()));

            Map<String, List<BudgetStyleMaterial>> spaceMaterialGroupData = new LinkedHashMap<>();

            tempSpaceMaterialGroupData.forEach((strKey, strValue) -> {
                List<BudgetStyleMaterial> tempSpaceMaterialList = new ArrayList<>();
                strValue.forEach((value) -> {
                    value.getBudgetStyleMaterialList().stream()
                            .filter(material -> (material.getConstructionType().equals(ConstructionType.SPACE)))
                            .forEach(material -> {
                                //???????????? ?????? ???????????? PUT
                                tempSpaceMaterialList.add(material);
                            });
                });
                spaceMaterialGroupData.put(strKey,tempSpaceMaterialList);
            });
            //???????????? ??????
            StyleEstimateResponse.CommonConstruction commonConstructions = CommonConstructionStyle(
                    commonMaterialGroupData);
            //???????????? ??????
            StyleEstimateResponse.SpaceConstruction spaceConstructions = SpaceConstructionStyle(
                    spaceMaterialGroupData);

            //?????? ?????? ??????(?????? , ??????)
            int totalCommonConstructionAmount = commonConstructions.getTotalCommonConstructionAmount();
            int totalSpaceConstructionAmount = spaceConstructions.getTotalSpaceConstructionAmount();

            //?????? ??????
            return StyleEstimateResponse.builder()
                    .spaceImagesList(spaceImage)
                    .totalConstructionAmount(
                            totalCommonConstructionAmount + totalSpaceConstructionAmount)
                    .commonConstruction(commonConstructions)
                    .spaceConstruction(spaceConstructions)
                    .build();
        } else {
            throw new CustomException(ResponseCode.BUDGET_NOT_FOUND);
        }
    }

    private StyleEstimateResponse.CommonConstruction CommonConstruction(
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> commonMaterialGroupData) {
        List<StyleEstimateResponse.CommonConstructionProcess> commonConstructionProcessList = new ArrayList<>();
        final int[] totalCommonConstructionAmount = {0};
        final Long[] styleMId = new Long[1];

        commonMaterialGroupData
                .forEach((strKey, strValue) -> {
                    List<StyleEstimateResponse.CommonMaterial> commonMaterialList = new ArrayList<>();

                    int groupCommonAmount = strValue.stream()
                            .mapToInt(BudgetStyleMaterial::getTotalAmount).sum();

                    totalCommonConstructionAmount[0] += groupCommonAmount;
                    strValue.forEach((value) -> {
                        if (value.getMaterialName() != null && !value.getMaterialName().trim()
                                .isEmpty()) { // ?????? ???????????? ???????????? (??????,??????)
                            String tempMaterialName = "";
                            if (value.getBudgetStyle().getSpaceType() != SpaceType.COMMON) {
                                if (value.getBudgetStyle().getSpaceDetailType() != null) {
                                    tempMaterialName =
                                            "[" + value.getBudgetStyle().getSpaceDetailType()
                                                    .getName()
                                                    + "]";
                                } else {
                                    tempMaterialName =
                                            "[" + value.getBudgetStyle().getSpaceType().getName()
                                                    + "]";
                                }
                            }
                            String materialName =
                                    (tempMaterialName != null && !tempMaterialName.trim()
                                            .isEmpty()) ? (tempMaterialName + " "
                                            + value.getMaterialName()) : value.getMaterialName();
                            commonMaterialList.add(
                                    StyleEstimateResponse.CommonMaterial.builder()
                                            .materialName(materialName)
                                            .build()
                            );
                        }
                    });
                    commonConstructionProcessList.add(
                            StyleEstimateResponse.CommonConstructionProcess.builder()
                                    .constructionAmount(groupCommonAmount)
                                    .constructionField(strKey.getName())
                                    .constructionFieldType(strKey.getCode())
                                    .material(commonMaterialList)
                                    .build()
                    );
                });

        StyleEstimateResponse.CommonConstruction commonConstruction = StyleEstimateResponse.CommonConstruction.builder()
                .totalCommonConstructionAmount(totalCommonConstructionAmount[0])
                .process(commonConstructionProcessList)
                .build();

        return commonConstruction;
    }

    private StyleEstimateResponse.SpaceConstruction SpaceConstruction(
            Map<String, List<BudgetStyleMaterial>> spaceMaterialGroupData) {
        List<StyleEstimateResponse.SpaceConstructionProcess> spaceConstructionProcessList = new ArrayList<>();
        final int[] totalSpaceConstructionAmount = {0};

        spaceMaterialGroupData.forEach((strKey, strValue) -> {
            Long styleMId = strValue.get(0).getBudgetStyleMId();
            List<StyleEstimateResponse.SpaceField> spaceFieldList = new ArrayList<>();

            int groupCommonAmount = strValue.stream()
                    .mapToInt(BudgetStyleMaterial::getTotalAmount).sum();

            totalSpaceConstructionAmount[0] += groupCommonAmount;

            //???????????? ??????????????? ??? ??????????????? ?????????
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> spaceGroupMaterial = strValue.stream()
                    .collect(groupingBy(BudgetStyleMaterial::getConstructionFieldType,
                            LinkedHashMap::new, Collectors.toList()));

            //??????????????? ????????? ???
            spaceGroupMaterial.forEach((strKey2, strValue2) -> {
                List<StyleEstimateResponse.SpaceMaterial> spaceMaterialList = new ArrayList<>();

                strValue2.forEach((value2) -> {
                    spaceMaterialList.add(
                            StyleEstimateResponse.SpaceMaterial.builder()
                                    .materialName(value2.getMaterialName())
                                    .build()
                    );
                });
                spaceFieldList.add(
                        StyleEstimateResponse.SpaceField.builder()
                                .constructionFieldType(strKey2.getCode())
                                .constructionField(strKey2.getName())
                                .material(spaceMaterialList)
                                .build()
                );
            });
            spaceConstructionProcessList.add(
                    StyleEstimateResponse.SpaceConstructionProcess.builder()
                            .constructionAmount(groupCommonAmount)
                            .spaceType(strKey)
                            .spaceName(SpaceAllType.valueOfCode(strKey).getName())
                            .fields(spaceFieldList)
                            .build()
            );
        });

        StyleEstimateResponse.SpaceConstruction spaceConstruction = StyleEstimateResponse.SpaceConstruction.builder()
                .totalSpaceConstructionAmount(totalSpaceConstructionAmount[0])
                .process(spaceConstructionProcessList)
                .build();

        return spaceConstruction;
    }

    private List<StyleEstimateResponse.SpaceImage> SpaceImage(
            List<BudgetEstimateInfo> tempEstimateInfoData) {
        List<StyleEstimateResponse.SpaceImage> spaceImage = tempEstimateInfoData.stream()
                .filter(entity -> (!entity.getBudgetStyle().getSpaceType()
                        .equals(SpaceType.COMMON)))
                .map(entity -> {
                    return StyleEstimateResponse.SpaceImage.builder()
                            .spaceName((entity.getBudgetStyle().getSpaceDetailType() != null)
                                    ? entity.getBudgetStyle().getSpaceDetailType().getName()
                                    : entity.getBudgetStyle().getSpaceType().getName())
                            .styleTitle(entity.getBudgetStyle().getStyleTitle())
                            .styleMainImageUrl(GlobalConstants.makeCdnUrl(
                                    entity.getBudgetStyle().getStyleMainImagePath()))
                            .styleSubImageUrl(GlobalConstants.makeCdnUrl(
                                    entity.getBudgetStyle().getStyleSubImagePath()))
                            .build();
                }).collect(Collectors.toList());

        return spaceImage;
    }

    private StyleEstimateResponse.CommonConstruction CommonConstructionStyle(
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> commonMaterialGroupData) {
        List<StyleEstimateResponse.CommonConstructionProcess> commonConstructionProcessList = new ArrayList<>();
        final int[] totalCommonConstructionAmount = {0};
        final Long[] styleMId = new Long[1];

        commonMaterialGroupData
                .forEach((strKey, strValue) -> {
                    styleMId[0] = strValue.get(0).getBudgetStyleMId();
                    List<StyleEstimateResponse.CommonMaterial> commonMaterialList = new ArrayList<>();

                    int groupCommonAmount = strValue.stream()
                            .mapToInt(BudgetStyleMaterial::getTotalAmount).sum();

                    totalCommonConstructionAmount[0] += groupCommonAmount;
                    strValue.forEach((value) -> {
                        if (value.getMaterialName() != null && !value.getMaterialName().trim()
                                .isEmpty()) { // ?????? ???????????? ???????????? (??????,??????)
                            String tempMaterialName = "";
                            if (value.getBudgetStyle().getSpaceType() != SpaceType.COMMON) {
                                if (value.getBudgetStyle().getSpaceDetailType() != null) {
                                    tempMaterialName =
                                            "[" + value.getBudgetStyle().getSpaceDetailType()
                                                    .getName()
                                                    + "]";
                                } else {
                                    tempMaterialName =
                                            "[" + value.getBudgetStyle().getSpaceType().getName()
                                                    + "]";
                                }
                            }
                            String materialName =
                                    (tempMaterialName != null && !tempMaterialName.trim()
                                            .isEmpty()) ? (tempMaterialName + " "
                                            + value.getMaterialName()) : value.getMaterialName();

                            commonMaterialList.add(
                                    StyleEstimateResponse.CommonMaterial.builder()
                                            .materialName(materialName)
                                            .build()
                            );
                        }
                    });
                    commonConstructionProcessList.add(
                            StyleEstimateResponse.CommonConstructionProcess.builder()
                                    .constructionAmount(groupCommonAmount)
                                    .constructionField(strKey.getName())
                                    .constructionFieldType(strKey.getCode())
                                    .material(commonMaterialList)
                                    .build()
                    );
                });

        StyleEstimateResponse.CommonConstruction commonConstruction = StyleEstimateResponse.CommonConstruction.builder()
                .totalCommonConstructionAmount(totalCommonConstructionAmount[0])
                .process(commonConstructionProcessList)
                .build();

        return commonConstruction;
    }

    private StyleEstimateResponse.SpaceConstruction SpaceConstructionStyle(
            Map<String, List<BudgetStyleMaterial>> spaceMaterialGroupData) {
        List<StyleEstimateResponse.SpaceConstructionProcess> spaceConstructionProcessList = new ArrayList<>();
        final int[] totalSpaceConstructionAmount = {0};

        spaceMaterialGroupData.forEach((strKey, strValue) -> {
            Long styleMId = strValue.get(0).getBudgetStyleMId();
            List<StyleEstimateResponse.SpaceField> spaceFieldList = new ArrayList<>();

            int groupCommonAmount = strValue.stream()
                    .mapToInt(BudgetStyleMaterial::getTotalAmount).sum();

            totalSpaceConstructionAmount[0] += groupCommonAmount;

            //???????????? ??????????????? ??? ??????????????? ?????????
            Map<ConstructionFieldType, List<BudgetStyleMaterial>> spaceGroupMaterial = strValue.stream()
                    .collect(groupingBy(BudgetStyleMaterial::getConstructionFieldType,
                            LinkedHashMap::new, Collectors.toList()));

            //??????????????? ????????? ???
            spaceGroupMaterial.forEach((strKey2, strValue2) -> {
                List<StyleEstimateResponse.SpaceMaterial> spaceMaterialList = new ArrayList<>();

                strValue2.forEach((value2) -> {
                    spaceMaterialList.add(
                            StyleEstimateResponse.SpaceMaterial.builder()
                                    .materialName(value2.getMaterialName())
                                    .build()
                    );
                });
                spaceFieldList.add(
                        StyleEstimateResponse.SpaceField.builder()
                                .constructionFieldType(strKey2.getCode())
                                .constructionField(strKey2.getName())
                                .material(spaceMaterialList)
                                .build()
                );
            });
            spaceConstructionProcessList.add(
                    StyleEstimateResponse.SpaceConstructionProcess.builder()
                            .constructionAmount(groupCommonAmount)
                            .spaceType(strKey)
                            .spaceName(SpaceAllType.valueOfCode(strKey).getName())
                            .fields(spaceFieldList)
                            .build()
            );
        });

        StyleEstimateResponse.SpaceConstruction spaceConstruction = StyleEstimateResponse.SpaceConstruction.builder()
                .totalSpaceConstructionAmount(totalSpaceConstructionAmount[0])
                .process(spaceConstructionProcessList)
                .build();

        return spaceConstruction;
    }

    private List<SpaceImage> SpaceImageStyle(List<BudgetStyle> budgetStyleList) {
        List<StyleEstimateResponse.SpaceImage> spaceImage = budgetStyleList.stream()
                .filter(entity -> (!entity.getSpaceType()
                        .equals(SpaceType.COMMON)))
                .map(entity -> {
                    return StyleEstimateResponse.SpaceImage.builder()
                            .spaceName((entity.getSpaceDetailType() != null)
                                    ? entity.getSpaceDetailType().getName()
                                    : entity.getSpaceType().getName())
                            .styleTitle(entity.getStyleTitle())
                            .styleMainImageUrl(
                                    GlobalConstants.makeCdnUrl(entity.getStyleMainImagePath()))
                            .styleSubImageUrl(
                                    GlobalConstants.makeCdnUrl(entity.getStyleSubImagePath()))
                            .build();
                }).collect(Collectors.toList());

        return spaceImage;
    }

}
