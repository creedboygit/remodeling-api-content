package com.hanssem.remodeling.content.api.service.event;


import com.hanssem.remodeling.content.api.controller.event.request.EventSearchDetailRequest;
import com.hanssem.remodeling.content.api.controller.event.request.EventSearchRequest;
import com.hanssem.remodeling.content.api.controller.event.response.EventConsultChannelResponse;
import com.hanssem.remodeling.content.api.controller.event.response.EventDetailResponse;
import com.hanssem.remodeling.content.api.controller.event.response.EventResponse;
import com.hanssem.remodeling.content.api.repository.client.HanssemMallClient;
import com.hanssem.remodeling.content.api.service.event.mapper.EventMapper;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventDetailVo;
import com.hanssem.remodeling.content.api.service.event.vo.HsMallEventListVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.hanssem.remodeling.content.common.util.HsMallResUtil;
import com.hanssem.remodeling.content.constant.EventStatusType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final HanssemMallClient hanssemMallClient;

    @Value("${content.event.exposure-days}")
    private Integer eventExposureDays;

    public PageResponse<EventResponse> findEventList(EventSearchRequest eventSearchRequest) {

        log.debug("### eventSearchRequest: {}, page: {}, size: {}", eventSearchRequest, eventSearchRequest.getPage(), eventSearchRequest.getSize());

        EventStatusType eventStatusType = Optional.ofNullable(eventSearchRequest.getEventStatusType())
                .orElseThrow(() -> new CustomException(ResponseCode.BAD_REQUEST, "필수 파라메터 누락: eventStatusType"));

        HsMallEventListVo hsMallEventListVo = hanssemMallClient.findEventList(eventStatusType.getCode()
                , eventSearchRequest.getEventMallType().getCode()
                , eventSearchRequest.getEventChannelType().getCode()
                , eventStatusType == EventStatusType.END || eventStatusType == EventStatusType.WIN ? eventExposureDays : null
                , eventSearchRequest.getPageStr()
                , eventSearchRequest.getSizeStr()
                , IsYn.Y.name());

        HsMallResUtil.checkResCode(hsMallEventListVo.getCode(), hsMallEventListVo.getMessage(), true);

        List<EventResponse> eventResponseList = EventMapper.INSTANCE.toEventResponseList(hsMallEventListVo.getPlanList());
        int totalCount = Integer.parseInt(hsMallEventListVo.getPagingMap().getTotalRecordCount());

        return PageResponse.of(eventResponseList, eventSearchRequest.getPageable(), totalCount);
    }

    public EventDetailResponse findEventDetail(long shpNo, EventSearchDetailRequest eventSearchDetailRequest, Long userId) {

        log.debug("### eventSearchDetailRequest: {}", eventSearchDetailRequest);

        EventStatusType eventStatusType = Optional.ofNullable(eventSearchDetailRequest.getEventStatusType())
                .orElseThrow(() -> new CustomException(ResponseCode.BAD_REQUEST, "필수 파라메터 누락: eventStatusType"));

        HsMallEventDetailVo hsMallEventDetailVo = hanssemMallClient.findEventDetail(
                String.valueOf(shpNo)
                , eventStatusType.getCode()
                , eventStatusType == EventStatusType.END || eventStatusType == EventStatusType.WIN ? eventExposureDays : null
                , userId == null ? null : String.valueOf(userId)
        );

        HsMallResUtil.checkResCode(hsMallEventDetailVo.getCode(), hsMallEventDetailVo.getMessage(), true);

        return EventMapper.INSTANCE.toEventDetailResponse(hsMallEventDetailVo);
    }

    public EventConsultChannelResponse findEventConsultChannel(long shpNo) {

        HsMallEventDetailVo hsMallEventDetailVo = hanssemMallClient.findEventDetail(
                String.valueOf(shpNo)
                , EventStatusType.ING.getCode()
                , null
                , null);

        HsMallResUtil.checkResCode(hsMallEventDetailVo.getCode(), hsMallEventDetailVo.getMessage(), true);

        return EventConsultChannelResponse.builder()
                .id(hsMallEventDetailVo.getPlanDetailMap().getShpNo())
                .title(hsMallEventDetailVo.getPlanDetailMap().getShpNm())
                .thumbUrl(hsMallEventDetailVo.getPlanDetailMap().getHorizontalType1BannerUrl())
                .build();
    }
}
