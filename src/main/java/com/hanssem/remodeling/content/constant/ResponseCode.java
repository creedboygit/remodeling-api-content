package com.hanssem.remodeling.content.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    OK(0, "OK"),
    SUCCESS(200, "SUCCESS"),
    NOT_FOUND(404, "찾을 수 없는 요청입니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),

    ERROR(9999, "오류가 발생했습니다. 잠시 후 다시 시도해 주세요."),
    ERROR_FEIGN_CLIENT(9998, "Feign Client 에러"),
    ERROR_FEIGN_CLIENT_404(9404, "Feign Client 에러: 404 Not Found"),

    //16000 ~ 16099 컨텐츠-공통
    NOT_FOUND_DATA(16001, "요청하신 데이터를 찾을 수 없습니다."),
    NOT_FOUND_LOGGER(16002, "Logger를 찾을 수 없습니다."),
    BAD_REQUEST_TOKEN(16003, "토큰 정보가 없습니다."),
    INVALID_MOBILE_NO(16004, "사용자의 휴대폰번호가 유효하지 않습니다."),
    INVALID_CUST_NO(16005, "유효하지 않은 사용자 입니다."),
    ERROR_NOTIFICATION_SEND(16006, "알림톡 발송을 실패했습니다."),

    //16100 ~ 16199 컨텐츠-상품
    GOODS_INVALID_CODE(16_100, "유효한 코드가 아닙니다."),
    GOODS_INVALID_GROUP_CODE(16_101, "유효한 그룹코드가 아닙니다."),
    GOODS_INVALID_STATE_CODE(16_101, "유효한 상태코드가 아닙니다."),
    GOODS_INVALID_BADGE_CODE(16_101, "유효한 뱃지코드가 아닙니다."),
    GOODS_INVALID_STYLE_CODE(16_101, "유효한 스타일코드가 아닙니다."),
    GOODS_DISCORD_GOODS_ID(16_101, "상품ID가 불일치 합니다."),
    GOODS_ID_IS_EMPTY(16_103, "상품 ID 입력해 주세요."),
    GOODS_CATEGORY_IS_EMPTY(16_104, "상품 카테고리 번호를 입력해 주세요."),
    GOODS_IMAGE_IS_EMPTY(16_105, "상품 이미지 입력해 주세요."),
    GOODS_EXCEED_STYLE_LIST(16_106, "상품 스타일 갯수 초과되었습니다."),

    //16200 ~ 16299 컨텐츠-공간별견적담기
    BUDGET_NOT_FOUND(16200, "[공간별예산감잡기] 해당하는 데이터가 없습니다."),
    BUDGET_ALREADY_CONSULT_STATE_CHANGE(16201, "[공간별예산감잡기] 이미 상담신청 완료 된 견적입니다."),
    BUDGET_INCONSISTENCY_USER_ID(16202, "[공간별예산감잡기] USER_ID 불일치"),

    //16300 ~ 16399 인테리어취향테스트
    FAVORITE_INVALID_PARAMS_IMAGES(16_300, "선호하는 이미지를 3개 선택해 주세요."),
    FAVORITE_INVALID_PARAMS_COLORS(16_301, "선호하는 컬러를 2개 선택해 주세요."),
    FAVORITE_INVALID_PARAMS_KEYWORDS(16_302, "선호하는 키워드를 3개 선택해 주세요."),
    FAVORITE_INVALID_PARAMS_MATERIALS(16_303, "선호하는 소재를 2개 선택해 주세요."),
    FAVORITE_INVALID_TAGS(16_304, "유효한 태그가 아닙니다."),
    FAVORITE_TEST_FEEDBACK_ERROR(16_305, "인테리어 취향 테스트 처리 중에 오류가 발생했습니다. 확인 후 다시 해주세요."),

    //16400 ~ 16499 전시
    DISPLAY_NOT_FOUND_DATA(16404, "해당하는 데이터가 없습니다."),

    CONSTRUCT_REQUIRED_PARAMS_OMITTED(16405, "필수 파라미터 누락"),

    ;
    /*
     * 10000 ~ 10999 게이트웨이
     * 11000 ~ 11999 인증
     * 12000 ~ 12999 공통
     * 13000 ~ 13999 회원
     * 14000 ~ 14499 상담
     * 14500 ~ 14999 리모델링매니저
     * 15000 ~ 15999 대리점
     * 16000 ~ 16099 컨텐츠-공통
     * 16100 ~ 16199 컨텐츠-상품
     * 16200 ~ 16299 컨텐츠-공간별견적담기
     * 16300 ~ 16399 컨텐츠-인테리어취향
     * 16400 ~ 16999 컨텐츠-전시 외
     * 17000 ~ 17999 알림
     */
    private final int code;
    private final String message;


}
