package com.hanssem.remodeling.content.api.controller.event.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "EventConsultChannelResponse", description = "이벤트 상담채널 정보 조회")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventConsultChannelResponse {


    @Schema(title = "id", description = "이벤트 아이디", type = "long", example = "1")
    private long id;
    @Schema(title = "title", description = "이벤트명", type = "string", example = "기획전_타이틀")
    private String title;
    @Schema(title = "thumbUrl", description = "이벤트 썸네일(12/14일 전체경로 처리 예정)", type = "string", example = "/plan/720/15/15832_P1.jpg")
    private String thumbUrl;

}
