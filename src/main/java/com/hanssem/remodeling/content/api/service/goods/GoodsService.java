package com.hanssem.remodeling.content.api.service.goods;

import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListResponse;
import com.hanssem.remodeling.content.api.controller.construct.request.ConstructCaseRequest;
import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseResponse;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsDisplayMashUpRequest;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsDisplayRequest;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsRequest;
import com.hanssem.remodeling.content.api.controller.goods.response.*;
import com.hanssem.remodeling.content.api.repository.goods.GoodsKeywordRecommendationTagRepository;
import com.hanssem.remodeling.content.api.repository.goods.GoodsQueryRepository;
import com.hanssem.remodeling.content.api.repository.goods.GoodsRepository;
import com.hanssem.remodeling.content.api.service.category.CategoryService;
import com.hanssem.remodeling.content.api.service.category.mapper.CategoryMapper;
import com.hanssem.remodeling.content.api.service.commoncode.CommoncodeService;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo;
import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo.CommoncodeResponse;
import com.hanssem.remodeling.content.api.service.construct.ConstructCaseService;
import com.hanssem.remodeling.content.api.service.construct.dto.ConstructCaseDto;
import com.hanssem.remodeling.content.api.service.goods.mapper.GoodsMapper;
import com.hanssem.remodeling.content.api.service.scrap.ScrapService;
import com.hanssem.remodeling.content.api.service.scrap.mapper.ScrapMapper;
import com.hanssem.remodeling.content.api.service.scrap.vo.ScrapExistenceVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.hanssem.remodeling.content.constant.CommoncodeType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface GoodsService {

    public GoodsDetailResponse getGoodsDetailInfo(AuthClaim authClaim, final Long goodsId);

    public GoodsScrapResponse getGoodsScrapInfo(AuthClaim authClaim, Long goodsId);

    public GoodsDetailInfoSimpleResponse getGoodsDetailSimpleInfo(final Long goodsId);

    public GoodsRequest makeGoodsDisplayCondition(GoodsDisplayRequest request);

    public Page<GoodsListResponse> getGoodsList(final GoodsRequest request);

    @Cacheable(unless = "#result.size() == 0", cacheManager = "goodsCacheManager", cacheNames = "getGoodsListDisplayMashUp", key = "{#request.goodsStateCode, #request.categoryNo, #request.page, #request.size}", condition = "#request.page == 0")
    public List<CategoryGoodsResponse> getGoodsListDisplayMashUp(GoodsDisplayMashUpRequest request);

    @CacheEvict(cacheManager = "goodsCacheManager", cacheNames = {"*"}, allEntries = true)
    public void clearGoodsCacheAll();
}
