package com.hanssem.remodeling.content.api.service.notification.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Schema(title = "카카오 알림톡/SMS 응답")
@Getter
@Setter
@ToString
public class NotificationTalkVo {

    private int code;
    private String message;
    private TmsResponse data;

    @Getter
    @Setter
    @ToString
    public static class TmsResponse {

        @Schema(title = "returnCode", description = "returnCode: 결과코드", type = "string", example = "S00")
        private String returnCode;

        @Schema(title = "returnMSG", description = "returnMSG: 결과 메세지", type = "string", example = "success")
        private String returnMSG;
    }

    public String getReturnCode() {
        return Optional.ofNullable(data)
                .map(TmsResponse::getReturnCode)
                .orElse(null);
    }

}
