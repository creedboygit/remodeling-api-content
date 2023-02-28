package com.hanssem.remodeling.content.admin.service.goods;

import com.hanssem.remodeling.content.admin.repository.goods.GoodsCategoryImportRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsCategoryMigrationRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsCategoryTobeRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsImageImportRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsImportRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsKeywordImportRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsSaleShopImportRepository;
import com.hanssem.remodeling.content.admin.service.ExcelImportAbstract;
import com.hanssem.remodeling.content.admin.service.common.CommonAdminService;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsCategoryDto;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsDto;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsImageDto;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsRenewalDto;
import com.hanssem.remodeling.content.admin.service.goods.mapper.GoodsAdminMapper;
import com.hanssem.remodeling.content.common.response.UploadResponse;
import com.hanssem.remodeling.content.constant.ImageType;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryMigration;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryTobe;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImageImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsSaleShopImport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoodsImportService extends ExcelImportAbstract  {

    private final CommonAdminService commonAdminService;
    private final GoodsImportRepository goodsImportRepository;
    private final GoodsKeywordImportRepository goodsKeywordImportRepository;
    private final GoodsSaleShopImportRepository goodsSaleShopImportRepository;
    private final GoodsCategoryImportRepository goodsCategoryImportRepository;
    private final GoodsImageImportRepository goodsImageImportRepository;
    private final GoodsCategoryMigrationRepository goodsCategoryMigrationRepository;
    private final GoodsCategoryTobeRepository goodsCategoryTobeRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(MultipartFile file, String extension) throws IOException {

        List<AddGoodsDto> goodsDtos = readGoodsDto(file, extension);

        for (AddGoodsDto goodsDto : goodsDtos) {
            Optional<GoodsImport> optionalGoods = goodsImportRepository.findById(goodsDto.getId());
            GoodsImport goodsImport;
            if (optionalGoods.isEmpty()) {
                goodsImport = GoodsAdminMapper.INSTANCE.toEntityImport(goodsDto);
                goodsImportRepository.save(goodsImport);
                addKeywords(goodsDto.getKeywordList(), goodsImport);
                addSaleShop(goodsDto, goodsImport);
                addImage(goodsDto, goodsImport);
            }
        }
    }

    /**
     * 카테고리 insert
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(MultipartFile file, String extension) throws IOException {

        List<AddGoodsCategoryDto> goodsCategoryDtos = readGoodsCategoryDto(file, extension);

        for (AddGoodsCategoryDto goodsCategoryDto : goodsCategoryDtos) {
            GoodsCategoryImport goodsCategoryImport = GoodsAdminMapper.INSTANCE.toCategoryEntity(goodsCategoryDto);
            goodsCategoryImportRepository.save(goodsCategoryImport);
        }
    }

    /**
     * 이미지 insert
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveImage(MultipartFile file, String extension) throws IOException {

        List<AddGoodsImageDto> goodsImageDtos = readGoodsImageDto(file, extension);

        for (AddGoodsImageDto goodsImageDto : goodsImageDtos) {
            GoodsImageImport goodsImageImport = GoodsAdminMapper.INSTANCE.toImageImportEntity(goodsImageDto);
            goodsImageImportRepository.save(goodsImageImport);
        }
    }

    /**
     * Image code insert
     */
    private void addImage(AddGoodsDto goodsDto, GoodsImport goodsImport) {
        List<String> imageList = Arrays.asList(goodsDto.getImagePath());
        for (String image : imageList) {
            GoodsImageImport goodsImageImport = GoodsImageImport.builder()
                .goodsId(goodsImport.getId())
                .imageSeq(1)
                .imageType(ImageType.SQUARE)
                .imagePathName(image)
                .build();
            goodsImageImportRepository.save(goodsImageImport);
        }
    }

    /**
     * Shop code insert
     */
    private void addSaleShop(AddGoodsDto goodsDto, GoodsImport goodsImport) {
        List<String> shopTypeCodeList = goodsDto.getSalesShopTypeCodeList();
        for (String shopCode : shopTypeCodeList) {
            GoodsSaleShopImport goodsSaleShopImport = GoodsSaleShopImport.builder()
                .goodsId(goodsImport.getId())
                .saleShopTypeCode(shopCode)
                .build();
            goodsSaleShopImportRepository.save(goodsSaleShopImport);
        }
    }

    /**
     * style insert
     */
    private void addStyle(String styleCode, GoodsImport goodsImport) {

        if (styleCode == null || styleCode.isEmpty()) return;

        List<GoodsKeywordImport> styles = goodsKeywordImportRepository.findByIdAndCodeName(goodsImport.getId(), "STYLE");
        if (styles.size() <= 0) {
            GoodsKeywordImport goodsKeywordImport = GoodsKeywordImport.builder()
                .goodsId(goodsImport.getId())
                .keywordType("STYLE")
                .keywordSeq(1)
                .keyword(styleCode)
                .build();
            goodsKeywordImportRepository.save(goodsKeywordImport);
        } else if (styles.size() == 1) {
            GoodsKeywordImport goodsKeywordImport = styles.get(0);
            goodsKeywordImport.setKeyword(styleCode);
            goodsKeywordImportRepository.save(goodsKeywordImport);
        } else {
            log.debug("keyword is exist. ({} - {})", goodsImport.getId(), styles.size());
        }
    }

    /**
     * keyword insert
     */
    private void addKeywords(List<String> keywordList, GoodsImport goodsImport) {

        if (keywordList == null || keywordList.isEmpty()) return;

        int index = 1;
        List<String> keywords = keywordList.stream().distinct().collect(Collectors.toList());
        for (String keyword : keywords) {
            List<GoodsKeywordImport> search = goodsKeywordImportRepository
                .findByIdAndName(goodsImport.getId(), "SEARCH", keyword);
            if (search.size() <= 0) {
                GoodsKeywordImport goodsKeywordImport = GoodsKeywordImport.builder()
                    .goodsId(goodsImport.getId())
                    .keywordType("SEARCH")
                    .keywordSeq(index++)
                    .keyword(keyword)
                    .build();
                goodsKeywordImportRepository.save(goodsKeywordImport);
            } else if (search.size() == 1) {
                GoodsKeywordImport goodsKeywordImport = search.get(0);
                goodsKeywordImport.setKeyword(keyword);
                goodsKeywordImportRepository.save(goodsKeywordImport);
            } else {
                log.debug("keyword is exist. ({})", goodsImport.toString());
            }
        }
    }

    /**
     * recommendTag insert
     */
    @Transactional
    protected void addRecommendKeywords(List<String> keywordList, GoodsImport goodsImport) {

        if (keywordList == null || keywordList.isEmpty()) return;

        int index = 1;
        List<String> keywords = keywordList.stream().distinct().collect(Collectors.toList());

        //기존 데이터 reset
        List<GoodsKeywordImport> beforeKeywords = goodsKeywordImportRepository.findByGoodsIdAndType(goodsImport.getId(), "RECOMMEND");
        goodsKeywordImportRepository.deleteAllInBatch(beforeKeywords);

        for (String keyword : keywords) {
            if (index >= 5) break; //max 5개까지만 처리
            List<GoodsKeywordImport> recommend = goodsKeywordImportRepository
                .findByIdAndName(goodsImport.getId(), "RECOMMEND", keyword);
            if ( recommend.size() <= 0) {
                GoodsKeywordImport goodsKeywordImport = GoodsKeywordImport.builder()
                    .goodsId(goodsImport.getId())
                    .keywordType("RECOMMEND")
                    .keywordSeq(index++)
                    .keyword(keyword)
                    .build();
                goodsKeywordImportRepository.save(goodsKeywordImport);
            } else if (recommend.size() == 1) {
                GoodsKeywordImport goodsKeywordImport = recommend.get(0);
                goodsKeywordImport.setKeyword(keyword);
                goodsKeywordImportRepository.save(goodsKeywordImport);
            } else {
                log.debug("keyword is exist. ({})", goodsImport.toString());
            }
        }
    }

    private List<AddGoodsDto> readGoodsDto(MultipartFile file, String extension) throws IOException {
        List<AddGoodsDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            AddGoodsDto data = new AddGoodsDto();

            data.setId(readLong(row, 0));
            data.setGoodsName(readString(row, 1));
            data.setGoodsStateCode(readString(row, 2));
            data.setSaleBeginDateTime(row.getCell(3).getLocalDateTimeCellValue());
            data.setSaleEndDateTime(row.getCell(4).getLocalDateTimeCellValue());
            data.setImagePath(readString(row, 5));
            data.setSystemCreateDateTime(row.getCell(6).getLocalDateTimeCellValue());
            data.setSystemConstructorId(readNumeric(row, 7));
            data.setSystemUpdateDateTime(row.getCell(8).getLocalDateTimeCellValue());
            data.setSystemUpdaterId(readNumeric(row, 9));
            data.setVideoUrl(readString(row, 10));
            String consultRequestYn = readString(row, 11);
            consultRequestYn = getConsultRequestYn(consultRequestYn);
            data.setConsultRequestYn(consultRequestYn);
            data.setGoodsGroupCode(readString(row, 12));
            data.setDisplayYn(readString(row, 13));
            data.setStandardCategoryNo(readInteger(row, 14));
            data.setMallGoodsNo(readInteger(row, 15));
            data.setWebPageContent(readString(row, 16));
            data.setMobilePageContent(readString(row, 17));
            data.setGoodsOutline(readString(row, 18));
            data.setSearchKeyword(readString(row, 19));
            data.setDescription(readString(row, 20));
            //21 BEST_YN은 Skip
            data.setVrYn(readString(row, 22));
            data.setEventYn(readString(row, 23));
            data.setSalesShopTypeCode(readString(row, 24));
            data.setGoodsBadgeTypeCode(readString(row, 25));
            //26,27 신규 컬럼
            data.setRecommendContent("");
            data.setRecommendTag("");

            dataList.add(data);
        }

        return dataList;
    }

    private String getConsultRequestYn(String consultRequestYn) {
        if (consultRequestYn == null || consultRequestYn.isEmpty()) {
            consultRequestYn = "N";
        }
        return consultRequestYn;
    }

    /**
     * 카테고리 읽기
     */
    private List<AddGoodsCategoryDto> readGoodsCategoryDto(MultipartFile file, String extension) throws IOException {
        List<AddGoodsCategoryDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            AddGoodsCategoryDto data = new AddGoodsCategoryDto();

            data.setGoodsId(readLong(row, 0));
            data.setCategoryNo(readInteger(row, 1));
            data.setCategoryLargeNo(readInteger(row, 2));
            data.setCategoryMiddleNo(readInteger(row, 3));
            data.setCategorySmallNo(readInteger(row, 4));
            data.setCategoryTinyNo(readInteger(row, 5));
            data.setCategoryLargeName(readString(row, 6));
            data.setCategoryMiddleName(readString(row, 7));
            data.setCategorySmallName(readString(row, 8));
            data.setCategoryTinyName(readString(row, 9));
            data.setCategoryDisplaySeq(readInteger(row, 10));
            //useYn pass
            //data.setUseYn(readString(row, 11));
            data.setSystemCreateDateTime(row.getCell(12).getLocalDateTimeCellValue());
            data.setSystemConstructorId(readNumeric(row, 13));
            data.setSystemUpdateDateTime(row.getCell(14).getLocalDateTimeCellValue());
            data.setSystemUpdaterId(readNumeric(row, 15));

            dataList.add(data);
        }
        return dataList;
    }

    private List<AddGoodsImageDto> readGoodsImageDto(MultipartFile file, String extension) throws IOException {
        List<AddGoodsImageDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
            Row row = worksheet.getRow(i);
            AddGoodsImageDto data = new AddGoodsImageDto();

            data.setGoodsId(readLong(row, 0));
            data.setImageSeq(readInteger(row, 1));
            data.setImageDirectory(readString(row, 2));
            data.setImageFile(readString(row, 3));
            data.setImagePathName(data.getImageDirectory() + "/" + data.getImageFile());
            data.setSystemCreateDateTime(row.getCell(4).getLocalDateTimeCellValue());
            data.setSystemConstructorId(readNumeric(row, 5));
            data.setSystemUpdateDateTime(row.getCell(6).getLocalDateTimeCellValue());
            data.setSystemUpdaterId(readNumeric(row, 7));

            dataList.add(data);
        }

        return dataList;
    }

    /**
     * 처리 순서
     * 1. ASIS 카테고리 테이블 전체 조회 (tb_goods_category_d)
     * 2. ASIS 세 카테고리 이름으로 TOBE 카테고리 조회 (loop)
     *   -> 2-1. TOBE 카테고리를 tb_goods_category_d_tobe에 insert
     */
    public void migrateCategory() {
        String ctgName = "";
        String sourceName = "";
        String targetName = "";
        Boolean isFound = false;

        List<GoodsCategoryImport> notFoundDataList = new ArrayList<>();
        List<GoodsCategoryImport> allCategories = goodsCategoryImportRepository.findAll();

        for (GoodsCategoryImport category : allCategories) {
            isFound = false;
            ctgName = StringUtils.replace(category.getCategoryTinyName(), " ", "");
            log.debug("category name : {}", category.getCategoryTinyName());

            List<GoodsCategoryTobe> tobeCategories = new ArrayList<>();

            if (category.getCategoryMiddleNo() == 16113) {
                // 예외처리 :: 리모델링 패키지 > 트렌드 제안
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("전체리모델링");

            } else if (category.getCategorySmallNo() == 16171) {
                // 예외처리 :: 리모델링 상품 > 욕실 > 슈퍼 > 슈퍼
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("슈퍼");

            } else if (category.getCategorySmallNo() == 16209) {
                // 예외처리 :: 리모델링 상품 > 조명 > 팬턴트등 > 팬턴트등
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("펜던트등");

            } else if (category.getCategorySmallNo() == 16211) {
                // 예외처리 :: 리모델링 상품 > 조명 > 벽등 & 직부등 > 벽등 & 직부등
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("벽등/직부등");

            } else if (category.getCategorySmallNo() == 16219) {
                // 예외처리 :: 리모델링 상품 > 벽마감재 > 몰딩 > 몰딩
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("몰딩");

            } else if (category.getCategorySmallNo() == 16185) {
                // 예외처리 :: 리모델링 상품 > 빌트인수납 > 맞춤붙박이장 > 맞춤붙박이장
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("맞춤붙박이장");

            } else if (category.getCategorySmallNo() == 16205) {
                // 예외처리 :: 리모델링 상품 > 창호 / 폴딩도어 > 창호엑세서리 > 창호엑세서리
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm("창호엑세서리");

            } else {
                // 정상처리
                tobeCategories = goodsCategoryTobeRepository.findByCtgNm(ctgName);
            }

            if (tobeCategories.size() == 1) {
                saveNewCategory(category, tobeCategories.get(0));
                isFound = true;
            } else if (tobeCategories.size() > 1) {
                for (GoodsCategoryTobe tobeCategory : tobeCategories) {
                    sourceName = tobeCategory.getCtgNm_L() + tobeCategory.getCtgNm_D();
                    targetName = category.getCategoryMiddleName() + category.getCategoryTinyName();
                    if (sourceName.equals(targetName)) {
                        saveNewCategory(category, tobeCategory);
                        isFound = true;
                        break;
                    }
                }
            }
            if (isFound == false) {
                notFoundDataList.add(category);
            }
        }

        log.debug("======================================");
        log.debug("NOT FOUND : {}", notFoundDataList.size());
        Collections.sort(notFoundDataList);
        for (GoodsCategoryImport allCategory : notFoundDataList) {
            log.debug("{}", allCategory.toString());
        }
        log.debug("======================================");

    }

    @Transactional(rollbackFor = Exception.class)
    protected void saveNewCategory(GoodsCategoryImport category, GoodsCategoryTobe tobeCategory) {
        /**
         * GoodsCategory table
         */
        GoodsCategoryMigration newCategory = GoodsAdminMapper.INSTANCE.entityToEntity(category);
        newCategory = GoodsAdminMapper.INSTANCE.toNewEntity(tobeCategory);
        newCategory.setId(null);
        newCategory.setGoodsId(category.getGoodsId());
        newCategory.setCategoryDisplaySeq(category.getCategoryDisplaySeq());
        newCategory.setSystemCreateDateTime(category.getSystemCreateDateTime());
        newCategory.setSystemConstructorId(category.getSystemConstructorId());
        newCategory.setSystemUpdateDatetime(category.getSystemUpdateDateTime());
        newCategory.setSystemUpdaterId(category.getSystemUpdaterId());
        goodsCategoryMigrationRepository.saveAndFlush(newCategory);

        /**
         * Goods table
         */
        Optional<GoodsImport> optionalGoodsImport = goodsImportRepository.findById(category.getGoodsId());
        GoodsImport goodsImport = optionalGoodsImport.get();
        log.debug("asia({}) -> tobe({})", goodsImport.getStandardCategoryNo(), newCategory.getCategoryNo());

        goodsImport.setStandardCategoryNo(newCategory.getCategoryNo());
        goodsImportRepository.save(goodsImport);
    }

    /**
     * 이미지 마이그레이션
     */
    public void migrateImage() throws Exception {
        String hanssemImageDomain = "https://image.hanssem.com/hsimg";
        String imageUrl;
        String s3key;
        int index = 0;

        /**
         * goods table
         */
        List<GoodsImport> goodsList = goodsImportRepository.findAll();
        for (GoodsImport goods : goodsList) {
            imageUrl = hanssemImageDomain + goods.getImagePath().replace("content/goods", "");
            s3key = goods.getImagePath();
            try {
                UploadResponse uploadResponse = commonAdminService.goodsImageUrlToUpload(imageUrl, s3key);
                log.debug("{}/{}-{}", ++index, goodsList.size(), uploadResponse.getFilePath());
            } catch (Exception ex) {
                log.debug("{}/{}-ERROR:{}", ++index, goodsList.size(), imageUrl);
            }
        }

        /**
         * image table
         */
//        index = 0;
//        List<GoodsImageImport> images = goodsImageImportRepository.findAll();
//        for (GoodsImageImport image : images) {
//            imageUrl = hanssemImageDomain + image.getImagePathName().replace("content/goods", "");
//            s3key = image.getImagePathName();
//            try {
//                UploadResponse uploadResponse = commonAdminService.goodsImageUrlToUpload(imageUrl, s3key);
//                log.debug("{}/{}-{}", ++index, images.size(), uploadResponse.getFilePath());
//            } catch (Exception ex) {
//                log.debug("{}/{}-ERROR:{}", ++index, images.size(), imageUrl);
//            }
//        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void migrateRenewal(MultipartFile file, String extension) throws IOException {

        List<AddGoodsRenewalDto> goodsRenewalDtos = readGoodsRenewalDto(file, extension);

        for (AddGoodsRenewalDto renewalDto : goodsRenewalDtos) {
            Optional<GoodsImport> optionalGoods = goodsImportRepository.findById(renewalDto.getId());

            if (optionalGoods.isPresent()) {
                GoodsImport goodsImport = optionalGoods.get();
                goodsImport.setConsultRequestYn(renewalDto.getConsultRequestYn());
                goodsImport.setGoodsBadgeTypeCode(renewalDto.getGoodsBadgeTypeCode());
                goodsImport.setRecommendContent(renewalDto.getRecommendContent());
                goodsImportRepository.save(goodsImport);

                addStyle(renewalDto.getGoodsStyleCode(), goodsImport);
                addKeywords(renewalDto.getRecommendTags().stream().toList(), goodsImport);
                addRecommendKeywords(renewalDto.getRecommendTags().stream().toList(), goodsImport);
            } else {
                log.debug("data not found. ({})", renewalDto.getId());
            }
        }
    }

    private List<AddGoodsRenewalDto> readGoodsRenewalDto(MultipartFile file, String extension) throws IOException {
        List<AddGoodsRenewalDto> dataList = new ArrayList<>();
        Workbook workbook = null;

        if (extension.equals("xlsx") || extension.equals("tsv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 3; i < worksheet.getPhysicalNumberOfRows(); i++) {


            Row row = worksheet.getRow(i);
            AddGoodsRenewalDto data = new AddGoodsRenewalDto();
            Set<String> tags = new HashSet<>();
            Set<String> keywords = new HashSet<>();

            data.setId(readLong(row, 0));
            data.setRecommendContent(readString(row, 3));
            for (int j = 4; j <= 8; j++) {
                String tag = readString(row, j);
                if (tag.isEmpty()) continue;
                tags.add(tag);
            }
            data.setRecommendTags(tags);

            data.setGoodsStyleCode(readString(row, 9));
            data.setGoodsBadgeTypeCode(readString(row, 11));

            String consultRequestYn = readString(row, 12);
            consultRequestYn = getConsultRequestYn(consultRequestYn);
            data.setConsultRequestYn(consultRequestYn);

            Arrays.stream(readString(row, 13).split(",")).distinct()
                .forEach(k -> keywords.add(k));
            data.setSearchKeyword(keywords);

            dataList.add(data);
        }

        return dataList;
    }

}




