package com.hanssem.remodeling.content.api.service.display.mapper;

import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentGdsResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentWithDataResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMainGnbResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMenuResponse;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallComponentListVo.MallDisplayComponent;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallComponentListVo.MallDisplayComponentGds;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallGnbVo.DisplayMallGnb;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallMenuVo;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DisplayMapper {

    DisplayMapper INSTANCE = Mappers.getMapper(DisplayMapper.class);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    DisplayComponentWithDataResponse toDisplayComponentWithDataResponse(MallDisplayComponent mallDisplayComponent);

    //자식 클래스를 사용하는 이슈로 default 정의. default 없이 셋팅으로 처리 가능여부 확인 필요.
    default List<DisplayComponentResponse> toDisplayComponentResponseList(List<MallDisplayComponent> mallDisplayComponentList) {

        if (mallDisplayComponentList == null) {
            return null;
        }

        List<DisplayComponentResponse> resultList = new ArrayList<>(mallDisplayComponentList.size());
        for (MallDisplayComponent mallDisplayComponent : mallDisplayComponentList) {
            resultList.add(toDisplayComponentResponse(mallDisplayComponent));
        }

        return resultList;
    }

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    DisplayComponentResponse toDisplayComponentResponse(MallDisplayComponent mallDisplayComponent);

    List<DisplayMainGnbResponse> toDisplayMainGnbResponseList(List<DisplayMallGnb> displayMallGnbList);

    @Mapping(target = "pageUrl", ignore = true)
    DisplayMainGnbResponse toDisplayMainGnbResponse(DisplayMallGnb displayMallGnb);

    List<DisplayComponentGdsResponse> toDisplayComponentGdsResponseList(List<MallDisplayComponentGds> mallDisplayComponentGdsList);

    DisplayMenuResponse toDisplayMenuResponse(HsMallMenuVo hsMallMenuVo);

    /**
     * 특정 필드를 매핑에서 제외하고 싶을 경우
     *
     * @Mapping(target = "memberId", ignore = true) 특정 필드의 값을 검증하여 적용하고 싶은 경우
     * @Mapping(target = "handselPay", expression = "java( dto.getHandselPay() > 0 ? dto.getHandselPay() :
     * entity.getHandselPay() )") BeanMapping 하단에 추가
     */
}
