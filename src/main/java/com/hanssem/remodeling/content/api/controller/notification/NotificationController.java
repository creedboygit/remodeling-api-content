package com.hanssem.remodeling.content.api.controller.notification;

import com.hanssem.remodeling.content.api.service.notification.NotificationService;
import com.hanssem.remodeling.content.constant.NotificationTalkType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알림")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/notification", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "카카오알림톡 전송",
            description = "카카오알림톡 전송.<br><br>"
                    + "* 응답코드<br>"
                    + "- 10003 / 16003 : 토큰이 누락되었습니다. / 토큰 정보가 없습니다.<br>"
                    + "- 16004 : 사용자의 휴대폰번호가 유효하지 않습니다.<br>"
                    + "- 16005 : 유효하지 않은 사용자 입니다.<br>"
    )
    @PostMapping(value = "/send-talk")
    public void sendNotificationTalk(@RequestParam @NotEmpty(message = "notificationTalkType은 필수입니다.") NotificationTalkType notificationTalkType) {

        notificationService.sendNotificationTalk(notificationTalkType);
    }


    @Deprecated
    @Operation(summary = "Push 전송",
            description = "Push 전송"
    )
    @PostMapping(value = "/send-push")
    public void sendPush() {
//        notificationService.sendNotificationPush();
    }
}
