package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum DisplayGnbViewType {
    REMODELING("리모델링"),
    MAIN("메인"),
    MALL("몰"),
    HOMEIDEA("홈아이디어");

    private final String desc;
}
