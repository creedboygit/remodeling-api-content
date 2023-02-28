package com.hanssem.remodeling.content.api.service.sample.mapper;

import com.hanssem.remodeling.content.api.controller.sample.request.PathTestRequest;
import com.hanssem.remodeling.content.api.controller.sample.response.PathTestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SampleMapper {

    SampleMapper INSTANCE = Mappers.getMapper(SampleMapper.class);

    //    image domain prefix 관련 샘플. domain + path
    @Mapping(target = "pathData", expression = "java( com.hanssem.remodeling.content.common.util.GlobalConstants.makeCdnUrl(request.getPathData()) )")
    PathTestResponse pathTestRequestToResponse2(PathTestRequest request);

    /**
     * 특정 필드를 매핑에서 제외하고 싶을 경우
     *
     * @Mapping(target = "memberId", ignore = true) 특정 필드의 값을 검증하여 적용하고 싶은 경우
     * @Mapping(target = "handselPay", expression = "java( dto.getHandselPay() > 0 ? dto.getHandselPay() :
     * entity.getHandselPay() )") BeanMapping 하단에 추가
     */
}
