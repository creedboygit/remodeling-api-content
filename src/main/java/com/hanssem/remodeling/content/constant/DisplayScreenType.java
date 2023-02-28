package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum DisplayScreenType {
    MOBILE("M", "모바일"),
    PC("P", "PC");

    private final String code;
    private final String desc;
}
