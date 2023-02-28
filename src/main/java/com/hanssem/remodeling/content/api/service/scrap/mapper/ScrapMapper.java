package com.hanssem.remodeling.content.api.service.scrap.mapper;

import com.hanssem.remodeling.content.api.controller.goods.response.GoodsScrapResponse;
import com.hanssem.remodeling.content.api.service.category.mapper.CategoryMapper;
import com.hanssem.remodeling.content.api.service.scrap.vo.ScrapExistenceVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ScrapMapper {

    ScrapMapper INSTANCE = Mappers.getMapper(ScrapMapper.class);

    GoodsScrapResponse toGoodsScrapResponse(ScrapExistenceVo scrapExistenceVo);
}
