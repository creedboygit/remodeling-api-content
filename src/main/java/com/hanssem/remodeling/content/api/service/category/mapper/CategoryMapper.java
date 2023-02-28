package com.hanssem.remodeling.content.api.service.category.mapper;

import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoWithChildrenResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryLocationResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.CategoryInfoResponse;
import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryListVo;
import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryLocationVo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "cateLocationMap", target = "categoryLocationMap")
    MallCategoryLocationResponse toMallCategoryLocationResponse(MallCategoryLocationVo mallCategoryLocationVo);

    MallCategoryListResponse toMallCategoryListResponse(MallCategoryListVo mallCategoryListVo);

    List<MallCategoryListDetailInfoWithChildrenResponse> toMallCategoryListDetailInfoWithChildrenResponse(List<MallCategoryListVo.MallCategoryListDetailApiResponse> categoryList);

    List<MallCategoryListDetailInfoResponse> toMallCategoryListDetailInfoResponse(List<MallCategoryListVo.MallCategoryListDetailApiResponse> categoryList);

    List<CategoryInfoResponse> toCategoryInfoResponse(List<MallCategoryListDetailInfoResponse> categoryList);
}
