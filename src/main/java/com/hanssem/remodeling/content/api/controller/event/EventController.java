package com.hanssem.remodeling.content.api.controller.event;

import com.hanssem.remodeling.content.api.controller.event.request.EventSearchDetailRequest;
import com.hanssem.remodeling.content.api.controller.event.request.EventSearchRequest;
import com.hanssem.remodeling.content.api.controller.event.response.EventConsultChannelResponse;
import com.hanssem.remodeling.content.api.controller.event.response.EventDetailResponse;
import com.hanssem.remodeling.content.api.controller.event.response.EventResponse;
import com.hanssem.remodeling.content.api.service.event.EventService;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.InetAddress;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이벤트")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final InetAddress inetAddress;
    private final AuthClaim authClaim; // Header 정보가 담겨 있는 객체입니다.

    private final EventService eventService;


    @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록 조회.<br><br>"
            + "진행중(ING)/종료(END)/당첨안내(WIN) 조회 가능<br><br>"
            + "* 백엔드 참고: <br>"
            + "   -> 어드민에서 일시중지/영구정지 상태는 목록대상에서 완전 제외된다. (eventStatusType.END로 조회되지 않음.)<br>"
            + "   -> eventStatusType.END는 기간만료를 대상으로 한다.<br>"
            + "   -> 종료/당첨안내 이벤트(END/WIN)의 경우, 종료일로 부터 days 조건은 서버에서 관린한다. 180days.<br>"
            , responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = PageResponse.class)))})
    @GetMapping(value = "")
    public PageResponse<EventResponse> findEventList(@Valid EventSearchRequest eventSearchRequest) {

        return eventService.findEventList(eventSearchRequest);
    }

    @Operation(summary = "이벤트 상세 조회", description = "이벤트 상세 조회."
            , responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = EventDetailResponse.class)))})
    @GetMapping(value = "/{shpNo}")
    public EventDetailResponse findEventDetail(
            @PathVariable(name = "shpNo")
            @Schema(title = "shpNo", description = "shpNo: 이벤트(기획전)번호", type = "integer", example = "15860")
            long shpNo,
            @Valid EventSearchDetailRequest eventSearchDetailRequest) {

        return eventService.findEventDetail(shpNo, eventSearchDetailRequest, authClaim.getUserId());
    }

    @Operation(summary = "이벤트 상담 채널 정보 조회", description = "이벤트 상담 채널 정보 조회.<br>"
            + "* 상담신청 상단의 채널 정보를 출력하기위한 API"
            , responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = EventConsultChannelResponse.class)))})
    @GetMapping(value = "/{shpNo}/consult-channel-info")
    public EventConsultChannelResponse findEventConsultChannel(
            @PathVariable(name = "shpNo")
            @Schema(title = "shpNo", description = "shpNo: 이벤트(기획전)번호", type = "integer", example = "15832")
            long shpNo) {

        return eventService.findEventConsultChannel(shpNo);
    }

}
