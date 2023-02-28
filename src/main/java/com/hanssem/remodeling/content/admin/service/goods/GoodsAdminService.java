package com.hanssem.remodeling.content.admin.service.goods;

import com.hanssem.remodeling.content.admin.controller.goods.request.AddGoodsRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.GoodsSearchRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsCategoryRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsCodeRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsImageRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsIsYnRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsKeywordRecommendationTagRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsKeywordSearchRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsKeywordStyleRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsSaleShopTypeRequest;
import com.hanssem.remodeling.content.admin.controller.goods.response.ExcelResponse;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsResponse;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsAdminQueryRepository;
import com.hanssem.remodeling.content.admin.repository.goods.GoodsAdminRepository;
import com.hanssem.remodeling.content.admin.service.goods.mapper.GoodsAdminMapper;
import com.hanssem.remodeling.content.api.service.commoncode.CommoncodeService;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo.CommoncodeResponse;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.AdminEventInfo;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.hanssem.remodeling.content.common.util.ContentAdminExcelUtility;
import com.hanssem.remodeling.content.constant.CommoncodeType;
import com.hanssem.remodeling.content.constant.ContentAdminGoodsExcelHeader;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategory;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImage;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordSearch;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordStyle;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsSaleShopType;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsAdminService {

    private static final String GOODS_STATE_TEMPORARY_CODE = "X1";
    private static final int EXCEL_MAX_ROWS = 100000;

    private final GoodsAdminQueryRepository goodsAdminQueryRepository;
    private final GoodsAdminRepository goodsAdminRepository;
    private final CommoncodeService commoncodeService;
    private final AdminEventInfo adminEventInfo;

    @Value("${content.goods.excel-tmp-path}")
    private String excelPath;

    @Transactional
    public Page<GoodsListResponse> searchGoods(GoodsSearchRequest request) {

        Page<Long> pageResult = goodsAdminQueryRepository.searchGoodsIds(request);
        List<GoodsListResponse> goodsList = goodsAdminQueryRepository.findAllById(pageResult.getContent());
        if (! goodsList.isEmpty()) {
            setStateCodeNames(CommoncodeType.GOODS_STATE_CODE, goodsList);
            setBadgeCodeNames(CommoncodeType.GOODS_BADGE_CODE, goodsList);
        }
        return PageResponse.of(goodsList, request.getPageable(), pageResult.getTotalElements());
    }

    @Transactional(readOnly = true)
    public ExcelResponse exportGoods(final GoodsSearchRequest request) {

        request.modifyPageAndSize(0, EXCEL_MAX_ROWS);

        Page<Long> pageResult = goodsAdminQueryRepository.searchGoodsIds(request);
        List<GoodsListResponse> goodsList = goodsAdminQueryRepository.findAllById(pageResult.getContent());

        if (!goodsList.isEmpty()) {
            setStateCodeNames(CommoncodeType.GOODS_STATE_CODE, goodsList);
            setBadgeCodeNames(CommoncodeType.GOODS_BADGE_CODE, goodsList);
        }

        File file = ContentAdminExcelUtility.exportExcel(
            excelPath,
            Arrays.stream(ContentAdminGoodsExcelHeader.values()).map(x -> x.getValue()).toList(),
            goodsList);

        return ExcelResponse.builder()
            .rows(goodsList.size())
            .file(file)
            .build();
    }

    @Transactional
    public GoodsResponse findGoodsById(Long goodsId) {
        Goods goods = goodsAdminRepository.findById(goodsId).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_DATA));
        GoodsResponse response = GoodsAdminMapper.INSTANCE.toResponse(goods);
        response.setGoodsStateCodeName(getCommoncodeName(CommoncodeType.GOODS_STATE_CODE, response.getGoodsStateCode()));
        response.setGoodsBadgeTypeCodeName(getCommoncodeName(CommoncodeType.GOODS_BADGE_CODE, response.getGoodsBadgeTypeCode()));
        return response;
    }

    @Transactional(readOnly = true)
    public CommoncodeVo getCommoncodeVo(CommoncodeType codeType) {
        return commoncodeService.getCommoncode(codeType);
    }

    @Transactional
    public int modifyGoodsStateCode(ModifyGoodsCodeRequest request) {
        isValidCode(CommoncodeType.GOODS_STATE_CODE, request.getCode());
        List<Goods> goodsList = goodsAdminQueryRepository.findByIdList(request.getGoodsIdList());
        if (goodsList == null) {
            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }
        goodsList.forEach(goods -> goods.modifyStateCode(request.getCode()));
        goodsList.forEach(goods -> goods.setSystemUpdaterId(adminEventInfo.getRegId()));
        goodsAdminRepository.saveAllAndFlush(goodsList);

        return goodsList.size();
    }

    @Transactional
    public int modifyGoodsBadgeCode(ModifyGoodsCodeRequest request) {

        if (request.getCode() != null && request.getCode().trim().length() > 0) {
            request.setCode(request.getCode().trim().toUpperCase());
        }
        isValidCode(CommoncodeType.GOODS_BADGE_CODE, request.getCode());

        List<Goods> goodsList = goodsAdminQueryRepository.findByIdList(request.getGoodsIdList());
        if (Objects.isNull(goodsList)) {
            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }
        goodsList.forEach(goods -> goods.modifyBadgeCode(request.getCode()));
        goodsList.forEach(goods -> goods.setSystemUpdaterId(adminEventInfo.getRegId()));
        goodsAdminRepository.saveAllAndFlush(goodsList);

        return goodsList.size();
    }

    @Transactional
    public int modifyGoodsVrYn(ModifyGoodsIsYnRequest request) {
        List<Goods> goodsList = goodsAdminQueryRepository.findByIdList(request.getGoodsIdList());
        if (Objects.isNull(goodsList)) {
            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }
        goodsList.forEach(goods -> goods.modifyVrYn(request.getIsYn()));
        goodsList.forEach(goods -> goods.setSystemUpdaterId(adminEventInfo.getRegId()));
        goodsAdminRepository.saveAllAndFlush(goodsList);

        return goodsList.size();
    }

    @Transactional
    public int modifyGoodsEventYn(ModifyGoodsIsYnRequest request) {
        List<Goods> goodsList = goodsAdminQueryRepository.findByIdList(request.getGoodsIdList());
        if (Objects.isNull(goodsList)) {
            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }
        goodsList.forEach(goods -> goods.modifyEventYn(request.getIsYn()));
        goodsList.forEach(goods -> goods.setSystemUpdaterId(adminEventInfo.getRegId()));
        goodsAdminRepository.saveAllAndFlush(goodsList);

        return goodsList.size();
    }

    @Transactional
    public Long addGoods(AddGoodsRequest request) {
        if (! request.getGoodsStateCode().equals(GOODS_STATE_TEMPORARY_CODE)) {
            isValidAddRequest(request);
        }
        Goods goods = GoodsAdminMapper.INSTANCE.toEntity(request);
        goods.setDefaultValues();
        goods.setSystemConstructorId(adminEventInfo.getRegId());
        goods.setSystemUpdaterId(adminEventInfo.getRegId());
        goods.associateGoods();
        goodsAdminRepository.save(goods);
        return goods.getId();
    }

    @Transactional
    public Long modifyGoods(ModifyGoodsRequest request, Long goodsId) {

        isValidModifyRequest(request, goodsId);
        removeDuplicatedRequestData(request);

        Goods goods = getGoods(goodsId);
        goods.setDefaultValues();
        changeGoodsValues(request, goods);
        goods.setSystemUpdaterId(adminEventInfo.getRegId());
        goods.associateGoods();

        //dup check
        List<GoodsKeywordRecommendationTag> keywordRecommendationTagList = goods.getKeywordRecommendationTagList();


        goodsAdminRepository.save(goods);

        return goods.getId();
    }

    @CacheEvict(cacheManager = "goodsCacheManager", cacheNames = {"*"}, allEntries = true)
    public void clearGoodsCacheAll() {
    }

    @CacheEvict(cacheManager = "displayCacheManager", cacheNames = {"*"}, allEntries = true)
    public void clearDisplayCacheAll() {
    }

    @CacheEvict(cacheManager = "categoryCacheManager", cacheNames = {"*"}, allEntries = true)
    public void clearCategoryCacheAll() {
    }

    private void removeDuplicatedRequestData(ModifyGoodsRequest request) {
        request.removeDuplicatedKeywordSearch();
        request.removeDuplicatedImage();
        request.removeDuplicatedRecommendationTagList();
    }

    private void changeGoodsValues(ModifyGoodsRequest request, Goods goods) {
        goods.modifyGoods(request);
        modifyCategory(request.getCategoryList(), goods);
        modifyImage(request.getImageList(), goods);
        modifyGoodsSaleShopType(request.getSaleShopTypeList(), goods);
        modifyGoodsKeywordSearch(request.getKeywordSearchList(), goods);
        modifyGoodsKeywordRecommendationTag(request.getKeywordRecommendationTagList(), goods);
        modifyGoodsKeywordStyle(request.getKeywordStyleList(), goods);
    }

    private void modifyGoodsKeywordStyle(List<ModifyGoodsKeywordStyleRequest> request, Goods goods) {
        List<GoodsKeywordStyle> response = GoodsAdminMapper.INSTANCE.toGoodsKeywordStyleEntity(request);
        goods.modifyGoodsKeywordStyle(response);
    }

    private void modifyGoodsKeywordRecommendationTag(List<ModifyGoodsKeywordRecommendationTagRequest> request, Goods goods) {
        List<ModifyGoodsKeywordRecommendationTagRequest> cleanRequestList = new ArrayList<>();
        List<GoodsKeywordRecommendationTag> origin = goods.getKeywordRecommendationTagList();
        int index = 0;
        boolean findKeyword = false;
        for (GoodsKeywordRecommendationTag source : origin) {
            index ++;
            findKeyword = false;
            for (ModifyGoodsKeywordRecommendationTagRequest target : request) {
                if (source.getKeywordName().equals(target.getKeywordName())) {
                    findKeyword = true;
                    log.debug("dup source : {}, target : {} ", source.getKeywordName(), target.getKeywordName());
                    continue;
                }
            }
            if (!findKeyword)
                cleanRequestList.add(request.get(index - 1));
        }
        List<GoodsKeywordRecommendationTag> response = GoodsAdminMapper.INSTANCE.toGoodsKeywordRecommendationTagEntity(request);
        goods.modifyGoodsKeywordRecommendationTag(response);
    }

    private void modifyGoodsKeywordSearch(List<ModifyGoodsKeywordSearchRequest> request, Goods goods) {
        List<GoodsKeywordSearch> response = GoodsAdminMapper.INSTANCE.toGoodsKeywordSearchEntity(request);
        goods.modifyGoodsKeywordSearch(response);
    }

    private void modifyGoodsSaleShopType(List<ModifyGoodsSaleShopTypeRequest> request, Goods goods) {
        List<GoodsSaleShopType> response = GoodsAdminMapper.INSTANCE.toGoodsSaleShopTypeEntity(request);
        goods.modifyGoodsSaleShopType(response);
    }

    private void modifyImage(List<ModifyGoodsImageRequest> request, Goods goods) {
        List<GoodsImage> response = GoodsAdminMapper.INSTANCE.toImageEntity(request);
        goods.modifyImage(response);
    }

    private void modifyCategory(List<ModifyGoodsCategoryRequest> request, Goods goods) {
        List<GoodsCategory> response = GoodsAdminMapper.INSTANCE.toCategoryEntityList(request);
        goods.modifyCategory(response);
    }

    private Goods getGoods(Long goodsId) {
        return goodsAdminRepository.findById(goodsId)
            .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND));
    }

    private void isValidCode(CommoncodeType codeType, String sourceCode) {
        if (sourceCode == null || sourceCode.isEmpty()) {
            return;
        }
        CommoncodeVo commoncodeVo = getCommoncodeVo(codeType);
        for (CommoncodeResponse code : commoncodeVo.getData()) {
            if (sourceCode.equalsIgnoreCase(code.getCodeValue())) {
                return;
            }
        }
        if (CommoncodeType.GOODS_GROUP_CODE.equals(codeType))
            throw new CustomException(ResponseCode.GOODS_INVALID_GROUP_CODE);
        else if (CommoncodeType.GOODS_STATE_CODE.equals(codeType))
            throw new CustomException(ResponseCode.GOODS_INVALID_STATE_CODE);
        else if (CommoncodeType.GOODS_BADGE_CODE.equals(codeType))
            throw new CustomException(ResponseCode.GOODS_INVALID_BADGE_CODE);
        else if (CommoncodeType.STYLE_CODE.equals(codeType))
            throw new CustomException(ResponseCode.GOODS_INVALID_STYLE_CODE);
        else
            throw new CustomException(ResponseCode.GOODS_INVALID_CODE);
    }

    private void isValidAddRequest(AddGoodsRequest request) {
        isValidCode(CommoncodeType.GOODS_GROUP_CODE, request.getGoodsGroupCode());
        isValidCode(CommoncodeType.GOODS_STATE_CODE, request.getGoodsStateCode());
        isValidCode(CommoncodeType.GOODS_BADGE_CODE, request.getGoodsBadgeTypeCode());

        isValidList(Objects.isNull(request.getCategoryList()), request.getCategoryList().isEmpty(),
            ResponseCode.GOODS_CATEGORY_IS_EMPTY);
        isValidList(Objects.isNull(request.getImageList()), request.getImageList().isEmpty(),
            ResponseCode.GOODS_IMAGE_IS_EMPTY);

        if(!Objects.isNull(request.getKeywordStyleList()) && !request.getKeywordStyleList().isEmpty()) {
            if (request.getKeywordStyleList().size() > 1) {
                throw new CustomException(ResponseCode.GOODS_EXCEED_STYLE_LIST);
            }
            isValidCode(CommoncodeType.STYLE_CODE, request.getKeywordStyleList().get(0).getKeywordName());
        }
    }


    private void isValidModifyRequest(ModifyGoodsRequest request, Long goodsId) {
        request.validGoodsId(goodsId);
        isValidCode(CommoncodeType.GOODS_GROUP_CODE, request.getGoodsGroupCode());
        isValidCode(CommoncodeType.GOODS_STATE_CODE, request.getGoodsStateCode());
        isValidCode(CommoncodeType.GOODS_BADGE_CODE, request.getGoodsBadgeTypeCode());
        isValidList(Objects.isNull(request.getCategoryList()), request.getCategoryList().isEmpty(),
            ResponseCode.GOODS_CATEGORY_IS_EMPTY);
        isValidList(Objects.isNull(request.getImageList()), request.getImageList().isEmpty(),
            ResponseCode.GOODS_IMAGE_IS_EMPTY);
        if(!Objects.isNull(request.getKeywordStyleList()) && !request.getKeywordStyleList().isEmpty()) {
            if (request.getKeywordStyleList().size() > 1) {
                throw new CustomException(ResponseCode.GOODS_EXCEED_STYLE_LIST);
            }
        }
    }

    private void isValidList(boolean aNull, boolean empty, ResponseCode goodsCategoryIsEmpty) {
        if (aNull || empty) {
            throw new CustomException(goodsCategoryIsEmpty);
        }
    }

    @Transactional(readOnly = true)
    public void setBadgeCodeNames(CommoncodeType codeType, List<GoodsListResponse> list) {
        CommoncodeVo commoncode = getCommoncodeVo(codeType);
        for (GoodsListResponse object : list) {
            if (Objects.isNull(object.getGoodsBadgeTypeCode()) || object.getGoodsBadgeTypeCode().isEmpty()) {
                object.setGoodsBadgeTypeCodeName(Strings.EMPTY);
                continue;
            }

            if (commoncode.getData() != null) {
                for (CommoncodeResponse code : commoncode.getData()) {
                    if (code.getCodeValue().equals(object.getGoodsBadgeTypeCode())) {
                        object.setGoodsBadgeTypeCodeName(code.getCodeValueName());
                        break;
                    }
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public void setStateCodeNames(CommoncodeType codeType, List<GoodsListResponse> list) {
        CommoncodeVo commoncode = getCommoncodeVo(codeType);
        for (GoodsListResponse object : list) {
            if (Objects.isNull(object.getGoodsStateCode()) || object.getGoodsStateCode().isEmpty()) {
                object.setGoodsStateCodeName(Strings.EMPTY);
                continue;
            }

            if (commoncode.getData() != null) {
                for (CommoncodeResponse code : commoncode.getData()) {
                    if (code.getCodeValue().equals(object.getGoodsStateCode())) {
                        object.setGoodsStateCodeName(code.getCodeValueName());
                        break;
                    }
                }
            }
        }
    }

    private String getCommoncodeName(CommoncodeType codeType, String source) {
        CommoncodeVo commoncode = getCommoncodeVo(codeType);
        for (CommoncodeResponse code : commoncode.getData()) {
            if (code.getCodeValue().equals(source)) {
                return code.getCodeValueName();
            }
        }
        return Strings.EMPTY;
    }

}
