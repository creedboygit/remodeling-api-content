package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum NotificationTalkType {
    SERVICE_CENTER("serviceCenter", "고객센터");

    private final String code;
    private final String desc;
}
