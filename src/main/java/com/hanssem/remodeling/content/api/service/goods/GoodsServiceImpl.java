package com.hanssem.remodeling.content.api.service.goods;

import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListResponse;
import com.hanssem.remodeling.content.api.controller.construct.request.ConstructCaseRequest;
import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseResponse;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsDisplayMashUpRequest;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsDisplayRequest;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsRequest;
import com.hanssem.remodeling.content.api.controller.goods.response.CategoryGoodsResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.CategoryInfoResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsDetailInfoResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsDetailInfoSimpleResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsDetailResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsKeywordRecommendationTagResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsScrapResponse;
import com.hanssem.remodeling.content.api.repository.goods.GoodsKeywordRecommendationTagRepository;
import com.hanssem.remodeling.content.api.repository.goods.GoodsQueryRepository;
import com.hanssem.remodeling.content.api.repository.goods.GoodsRepository;
import com.hanssem.remodeling.content.api.service.category.CategoryService;
import com.hanssem.remodeling.content.api.service.category.mapper.CategoryMapper;
import com.hanssem.remodeling.content.api.service.commoncode.CommoncodeService;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo;
import com.hanssem.remodeling.content.api.service.construct.ConstructCaseService;
import com.hanssem.remodeling.content.api.service.goods.mapper.GoodsMapper;
import com.hanssem.remodeling.content.api.service.scrap.ScrapService;
import com.hanssem.remodeling.content.api.service.scrap.vo.ScrapExistenceVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.hanssem.remodeling.content.constant.CommoncodeType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    private final GoodsQueryRepository goodsQueryRepository;

    private final GoodsKeywordRecommendationTagRepository recommendationTagRepository;

    private final CategoryService categoryService;

    private final ConstructCaseService constructCaseService;

    private final ScrapService scrapService;

    private final CommoncodeService commoncodeService;

    public GoodsDetailResponse getGoodsDetailInfo(AuthClaim authClaim, final Long goodsId) {

        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_DATA));
        GoodsDetailInfoResponse goodsResponse = GoodsMapper.INSTANCE.convertGoodsToResponse(goods);

        ScrapExistenceVo scrap = scrapService.getScrapExistence(authClaim.getAuthorization(), authClaim.getUserId(), goodsId);

        goodsResponse.setScrapYn(scrap.isData() ? "Y" : "N");

        return GoodsDetailResponse.builder()
                .goods(goodsResponse)
                .build();
    }

    public GoodsScrapResponse getGoodsScrapInfo(AuthClaim authClaim, Long goodsId) {

        log.debug("#authClaim = " + authClaim.toString());

        ScrapExistenceVo scrapExistence = scrapService.getScrapExistence(authClaim.getAuthorization(), authClaim.getUserId(), goodsId);
        return new GoodsScrapResponse(scrapExistence.isData());
    }

    private PageResponse<ConstructCaseResponse> getConstructCase(Goods goods) {

        ConstructCaseRequest constructCaseRequest = ConstructCaseRequest.builder()
                .goodsId(String.valueOf(goods.getId())).page("1").size("4").sort("R").build();

        PageResponse<ConstructCaseResponse> constructCaseResponse = constructCaseService.getConstructCase(constructCaseRequest);
        return constructCaseResponse;
    }

    public GoodsDetailInfoSimpleResponse getGoodsDetailSimpleInfo(final Long goodsId) {

        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_DATA));

        return GoodsDetailInfoSimpleResponse.builder()
                .id(goods.getId())
                .goodsName(goods.getGoodsName())
                .imagePathName(goods.getImagePathName())
                .build();
    }

    /**
     * 카테고리 레벨 구하기
     */
    public GoodsRequest makeGoodsDisplayCondition(GoodsDisplayRequest request) {

        GoodsRequest listRequest = new GoodsRequest(request.getPage(), request.getSize(), request.getSorts());
        getGoodsListByCategoryLevel(listRequest, request.getCategoryNo());


        listRequest.setGoodsStateCode(request.getGoodsStateCode());
        listRequest.setGoodsBadgeTypeCode(request.getGoodsBadgeTypeCode());
        listRequest.initRequestParam();

        return listRequest;
    }

    private void getGoodsListByCategoryLevel(GoodsRequest listRequest, int categoryNo) {

        int categoryLevel = categoryService.getMallCategoryLocation(categoryNo).getCategoryLocationMap().getCategoryLevel();

        if (categoryLevel == 0) {
            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }

        switch (categoryLevel) {
            case 3 :
                listRequest.setCategoryLargeNo(categoryNo);
                break;
            case 4 :
                listRequest.setCategoryMiddleNo(categoryNo);
                break;
            case 5 :
                listRequest.setCategorySmallNo(categoryNo);
                break;
            default :
                throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }
    }

    public Page<GoodsListResponse> getGoodsList(final GoodsRequest request) {

        Page<Long> pageResult = goodsQueryRepository.getGoodsIds(request);
        List<GoodsListResponse> goodsList = goodsQueryRepository.findAllById(pageResult.getContent(), request);
        List<GoodsKeywordRecommendationTag> allRecommendTagList = recommendationTagRepository.findAllByGoodsId(goodsList.stream().map(x -> x.getId()).toList());
        appendCodeNameAndTags(goodsList, allRecommendTagList);

        return PageResponse.of(goodsList, request.getPageable(), pageResult.getTotalElements());
    }

    private void appendCodeNameAndTags(List<GoodsListResponse> goodsList, List<GoodsKeywordRecommendationTag> allRecommendTagList) {

        setStateCodeNames(CommoncodeType.GOODS_STATE_CODE, goodsList);
        setBadgeCodeNames(CommoncodeType.GOODS_BADGE_CODE, goodsList);

        for (GoodsListResponse goods : goodsList) {
            goods.setRecommendationTagList(allRecommendTagList.stream()
                    .filter(x -> x.getGoods().getId().equals(goods.getId()))
                    .map(x -> GoodsKeywordRecommendationTagResponse.of(x.getKeywordName()))
                    .toList());
        }
    }

    public List<CategoryGoodsResponse> getGoodsListDisplayMashUp(GoodsDisplayMashUpRequest request) {

        List<CategoryGoodsResponse> tempCategory = new ArrayList<>();

        List<CategoryInfoResponse> categories = getCategories(request.getCategoryNo());

        for (CategoryInfoResponse category : categories) {

            CategoryGoodsResponse goodsCategory = new CategoryGoodsResponse();
            goodsCategory.setCategory(category);

            GoodsRequest listRequest = new GoodsRequest(request.getPage(), 5, request.getSorts());
            getGoodsListByCategoryLevel(listRequest, category.getCategoryNo());
            listRequest.initRequestParam();
            listRequest.setGoodsStateCode(request.getGoodsStateCode());

            Page<GoodsListResponse> goods = getGoodsList(listRequest);
            List<GoodsListResponse> content = goods.getContent();

            category.setGoods(content);
            tempCategory.add(goodsCategory);
        }

        return tempCategory;
    }

    public void clearGoodsCacheAll() {
    }

    private List<CategoryInfoResponse> getCategories(int categoryNo) {

        // 카테고리 가져오기
        MallCategoryListResponse childCategory = categoryService.getMallApiCategoryList(categoryNo, "child");
        List<MallCategoryListDetailInfoResponse> categoryList = childCategory.getCategoryList();

        return CategoryMapper.INSTANCE.toCategoryInfoResponse(categoryList);
    }

    @Transactional(readOnly = true)
    protected CommoncodeVo getCommoncodeVo(CommoncodeType codeType) {
        CommoncodeVo commoncode = commoncodeService.getCommoncode(codeType);
        return commoncode;
    }

    private void setBadgeCodeNames(CommoncodeType codeType, List<GoodsListResponse> list) {
        CommoncodeVo commoncode = getCommoncodeVo(codeType);
        for (GoodsListResponse object : list) {
            if (Objects.isNull(object.getGoodsBadgeTypeCode()) || object.getGoodsBadgeTypeCode().isEmpty()) {
                object.setGoodsBadgeTypeCodeName(Strings.EMPTY);
                continue;
            }
            for (CommoncodeVo.CommoncodeResponse code : commoncode.getData()) {
                if (code.getCodeValue().equals(object.getGoodsBadgeTypeCode())) {
                    object.setGoodsBadgeTypeCodeName(code.getCodeValueName());
                    break;
                }
            }
        }
    }

    private void setStateCodeNames(CommoncodeType codeType, List<GoodsListResponse> list) {
        CommoncodeVo commoncode = getCommoncodeVo(codeType);
        for (GoodsListResponse object : list) {
            if (Objects.isNull(object.getGoodsStateCode()) || object.getGoodsStateCode().isEmpty()) {
                object.setGoodsStateCodeName(Strings.EMPTY);
                continue;
            }
            for (CommoncodeVo.CommoncodeResponse code : commoncode.getData()) {
                if (code.getCodeValue().equals(object.getGoodsStateCode())) {
                    object.setGoodsStateCodeName(code.getCodeValueName());
                    break;
                }
            }
        }
    }

    private String getCommoncodeName(CommoncodeType codeType, String source) {
        CommoncodeVo commoncode = getCommoncodeVo(codeType);
        for (CommoncodeVo.CommoncodeResponse code : commoncode.getData()) {
            if (code.getCodeValue().equals(source)) {
                return code.getCodeValueName();
            }
        }
        return Strings.EMPTY;
    }
}
