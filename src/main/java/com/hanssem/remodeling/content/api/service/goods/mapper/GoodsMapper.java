package com.hanssem.remodeling.content.api.service.goods.mapper;

import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.*;
import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategory;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImage;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordSearch;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsSaleShopType;
import java.util.List;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GoodsMapper {

    GoodsMapper INSTANCE = Mappers.getMapper(GoodsMapper.class);

    @Mapping(source="keywordRecommendationTagList", target="recommendationTagList")
    GoodsDetailInfoResponse convertGoodsToResponse(Goods goods);

    List<CategoryResponse> convertGoodsCategoryToResponse(List<GoodsCategory> goodsCategory);

    List<GoodsImageResponse> convertGoodsImageToResponse(List<GoodsImage> goodsImageList);

    List<GoodsKeywordSearchResponse> convertGoodsKeywordToResponse(List<GoodsKeywordSearch> goodsKeywordSearchList);

    List<GoodsKeywordRecommendationTagResponse> convertGoodsKeywordRecommendationTagToResponse(List<GoodsKeywordRecommendationTag> goodsKeywordList);

    List<GoodsSaleShopTypeResponse> convertGoodsSaleShopTypeToResponse(List<GoodsSaleShopType> goodsSaleShopTypeList);

    List<GoodsSimpleResponse> toGoodsListResponse(List<GoodsListResponse> goodsList);
}

