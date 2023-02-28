package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum DisplayDataSearchType {
    COMPONENT_LAYOUT_CD("componentLayoutCd"),
    AD_TYPE("adType");

    private final String desc;
}
