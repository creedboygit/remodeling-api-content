package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentAdminGoodsExcelHeader {

    GOODS_ID("상품번호"),
    GOODS_NAME("상품명"),
    IMAGE_URL("이미지URL"),
    GOODS_STATE_CODE_NAME("상품상태"),
    GOODS_BADGE_TYPE_CODE_NAME("상품뱃지유형"),
    STANDARD_CATEGORY_NO("규격카테고리NO"),
    EVENT_YN("이벤트여부"),
    CONSULT_REQUEST_YN("상담신청여부"),
    DISPLAY_YN("디스플레이여부"),
    VR_YN("VR여부"),
    STANDARD_CATEGORY_NAME("규격카테고리명"),
    STYLE_CODE("스타일코드");

    private String value;
}
