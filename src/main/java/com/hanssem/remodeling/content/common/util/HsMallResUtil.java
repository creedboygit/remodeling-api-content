package com.hanssem.remodeling.content.common.util;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HsMallResUtil {

    private static final String HTTP_200 = "200";
    private static final String HTTP_404 = "404";

    private static final String HTTP_401 = "401";
    private static final String HTTP_402 = "402";

    public static void checkResCode(String code, String message, boolean is404Error) {

        if (HTTP_200.equals(code)) {
            return;
        }

        log.debug("# Hanssem Mall client - 응답 code: {}, message: {}", code, message);
        if (HTTP_404.equals(code)) {
            if (!is404Error) {
                log.debug("# 404 is not error");
                return;
            } else {
                throw new CustomException(ResponseCode.DISPLAY_NOT_FOUND_DATA);
            }
        }
        if (HTTP_401.equals(code) || HTTP_402.equals(code)) {
            throw new CustomException(ResponseCode.BAD_REQUEST, message);
        } else {
            throw new CustomException(ResponseCode.ERROR, message);
        }
    }
}
