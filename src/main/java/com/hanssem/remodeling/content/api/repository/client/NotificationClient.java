package com.hanssem.remodeling.content.api.repository.client;

import com.hanssem.remodeling.content.api.service.notification.dto.NotificationSendUserReqDto;
import com.hanssem.remodeling.content.api.service.notification.vo.NotificationTalkVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service-client", url = "${feign.baseUrl.notification-service-client}")
public interface NotificationClient {

    @PostMapping(value = "/api/v1/notification/user")
    NotificationTalkVo sendNotificationUser(@RequestBody NotificationSendUserReqDto notificationSendUserReqDto);

}
