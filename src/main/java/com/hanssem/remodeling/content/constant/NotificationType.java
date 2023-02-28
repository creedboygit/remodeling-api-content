package com.hanssem.remodeling.content.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    APP("앱푸시"),
    TMS("카카오알림톡");

    private final String desc;

}
