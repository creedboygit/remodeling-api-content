package com.hanssem.remodeling.content.api.service.notification.util;

public class NotificationUtil {

    public static final String DEFAULT_MOBILE_NO = "01000000000";
    public static final String RETURN_CODE_OK = "S00";

    public static final String SERVICE_CENTER_TEMPLATE_CODE = "AT_20230203172602";
    public static final String SERVICE_CENTER_URL_KEY = "#{URL}";
    public static final String SERVICE_CENTER_URL_VALUE = "http://pf.kakao.com/_pMxfxbu/chat";
    public static final String SERVICE_CENTER_TMS_MESSAGE = """
            안녕하세요.
            한샘 온라인 고객센터 입니다.
                        
            * 카카오톡 상담시간 운영안내
            - 평일 09:00 ~ 17:30 
            (주말&공휴일 운영제외)
                        
            * 카카오톡 상담방법
            - 한샘 온라인 고객센터 연결
            #{URL}
                        
            감사합니다.""";

}
