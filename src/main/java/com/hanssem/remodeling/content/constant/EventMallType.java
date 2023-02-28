package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum EventMallType {
    REMODELING("07", "리모델링"),
    DOTCOM("06", "한샘닷컴");

    private final String code;
    private final String desc;
}
