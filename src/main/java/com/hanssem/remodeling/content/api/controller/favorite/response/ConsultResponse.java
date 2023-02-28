package com.hanssem.remodeling.content.api.controller.favorite.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ConsultInfo", description = "빠른상담신청정보 객체")
public class ConsultResponse {

    @Schema(title = "channelId", description = "취향테스트 스타일 ID", type = "Long", example = "1")
    private Long channelId;
    @Schema(title = "channelType", description = "상담신청 채널타입", type = "String", example = "OP10-01")
    private String channelType;

}

