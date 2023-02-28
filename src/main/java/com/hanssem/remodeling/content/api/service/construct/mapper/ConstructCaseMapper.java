package com.hanssem.remodeling.content.api.service.construct.mapper;

import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseAllResponse;
import com.hanssem.remodeling.content.api.service.construct.vo.MallConstructCaseVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConstructCaseMapper {

    ConstructCaseMapper INSTANCE = Mappers.getMapper(ConstructCaseMapper.class);

    ConstructCaseAllResponse toConstructCaseListResponse(MallConstructCaseVo mallConstructCaseVo);
}
