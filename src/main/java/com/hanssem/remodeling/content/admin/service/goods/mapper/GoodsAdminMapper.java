package com.hanssem.remodeling.content.admin.service.goods.mapper;

import com.hanssem.remodeling.content.admin.controller.goods.request.AddGoodsRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsCategoryRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsImageRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsKeywordRecommendationTagRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsKeywordSearchRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsKeywordStyleRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsSaleShopTypeRequest;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsResponse;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsCategoryDto;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsDto;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsImageDto;
import com.hanssem.remodeling.content.admin.service.goods.dto.AddGoodsRenewalDto;
import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategory;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryMigration;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryTobe;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImage;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImageImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImport;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordSearch;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordStyle;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsSaleShopType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GoodsAdminMapper {
    GoodsAdminMapper INSTANCE = Mappers.getMapper(GoodsAdminMapper.class);

    GoodsCategoryImport toCategoryEntity(AddGoodsCategoryDto goodsCategoryDto);
    GoodsImageImport toImageImportEntity(AddGoodsImageDto goodsImageDto);
    GoodsImport toEntityImport(AddGoodsDto goodsDto);
    GoodsCategoryMigration entityToEntity(GoodsCategoryImport category);


    GoodsListResponse toListResponse(Goods goods);

    @Mapping(source = "ctgNo", target = "categoryNo")
    @Mapping(source = "ctgNm_D", target = "categoryTinyName")
    @Mapping(source = "ctgNm_S", target = "categorySmallName")
    @Mapping(source = "ctgNm_M", target = "categoryMiddleName")
    @Mapping(source = "ctgNm_L", target = "categoryLargeName")
    @Mapping(source = "ctgNo_D", target = "categoryTinyNo")
    @Mapping(source = "ctgNo_S", target = "categorySmallNo")
    @Mapping(source = "ctgNo_M", target = "categoryMiddleNo")
    @Mapping(source = "ctgNo_L", target = "categoryLargeNo")
    GoodsCategoryMigration toNewEntity(GoodsCategoryTobe categoryTobe);


    GoodsResponse toResponse(Goods goods);


    List<GoodsCategory> toCategoryEntityList(List<ModifyGoodsCategoryRequest> request);
    List<GoodsImage> toImageEntity(List<ModifyGoodsImageRequest> request);
    List<GoodsSaleShopType> toGoodsSaleShopTypeEntity(List<ModifyGoodsSaleShopTypeRequest> request);
    List<GoodsKeywordSearch> toGoodsKeywordSearchEntity(List<ModifyGoodsKeywordSearchRequest> request);
    List<GoodsKeywordStyle> toGoodsKeywordStyleEntity(List<ModifyGoodsKeywordStyleRequest> request);
    List<GoodsKeywordRecommendationTag> toGoodsKeywordRecommendationTagEntity(
        List<ModifyGoodsKeywordRecommendationTagRequest> request);

    Goods toEntity(AddGoodsRequest request);

    GoodsImport toEntityImport(AddGoodsRenewalDto renewalDto);
}
