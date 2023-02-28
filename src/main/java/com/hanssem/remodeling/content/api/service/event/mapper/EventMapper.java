package com.hanssem.remodeling.content.api.service.event.mapper;

import com.hanssem.remodeling.content.api.controller.event.response.EventDetailResponse;
import com.hanssem.remodeling.content.api.controller.event.response.EventDetailResponse.EventDetail;
import com.hanssem.remodeling.content.api.controller.event.response.EventDetailResponse.EventTemplate;
import com.hanssem.remodeling.content.api.controller.event.response.EventResponse;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventDetailVo;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventDetailVo.PlanDetailMap;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventDetailVo.PlanTemplateMap;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventListVo.EventPlanVo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);


    List<EventResponse> toEventResponseList(List<EventPlanVo> eventPlanVoList);

    @Mapping(source = "planDetailMap", target = "eventDetail")
    @Mapping(source = "planTemplateMap", target = "eventTemplate")
    EventDetailResponse toEventDetailResponse(HsMallEventDetailVo hsMallEventDetailVo);

    EventDetail toResponse(PlanDetailMap planDetailMap);

    EventTemplate toResponse(PlanTemplateMap planTemplateMap);

    /**
     * 특정 필드를 매핑에서 제외하고 싶을 경우
     *
     * @Mapping(target = "memberId", ignore = true) 특정 필드의 값을 검증하여 적용하고 싶은 경우
     * @Mapping(target = "handselPay", expression = "java( dto.getHandselPay() > 0 ? dto.getHandselPay() :
     * entity.getHandselPay() )") BeanMapping 하단에 추가
     */
}
