package com.hanssem.remodeling.content.api.service.notification.dto;

import com.hanssem.remodeling.content.constant.YnType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "알림톡 발송 파라미터", description = "알림톡 발송 파라미터")
@Getter
@Setter
public class NotificationPushReqDto {

    private String title;
    private String appMessage;
    private String webUrl;
    private String imageUrl;
    private String appLinkChannel;
    private String appLinkTarget;
    private String appLinkVal;
    private YnType ignoreOptInAgree = YnType.N;

}