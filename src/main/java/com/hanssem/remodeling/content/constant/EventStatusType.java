package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum EventStatusType {
    ING("081", "진행중이벤트"),
    END("082", "종료이벤트"),
    WIN("09", "당첨안내");

    private final String code;
    private final String desc;

}
