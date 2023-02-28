package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum HsMallAllType {
    REMODELING("REMODELING", "리모델링"),
    MAIN("MAIN", "메인"),
    MALL("MALL", "몰"),
    HOMEIDEA("HOMEIDEA", "홈아이디어"),
    ALL("ALL", "전체");

    private final String code;
    private final String desc;
}
