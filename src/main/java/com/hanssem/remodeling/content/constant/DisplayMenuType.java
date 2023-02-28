package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum DisplayMenuType {
    LINK("LINK", "링크텍스트"),
    CAT("CAT", "카테고리"),
    TEMP("TEMP", "전시템플릿");

    private final String code;
    private final String desc;
}
