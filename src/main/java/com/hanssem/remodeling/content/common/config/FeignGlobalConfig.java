package com.hanssem.remodeling.content.common.config;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.MdcType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.Retryer.Default;
import feign.codec.ErrorDecoder;
import java.util.Optional;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class FeignGlobalConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {

            if (response.status() == HttpStatus.NOT_FOUND.value()) {
                return new CustomException(ResponseCode.ERROR_FEIGN_CLIENT_404);
            }

            ResponseCode errorFeinClient = ResponseCode.ERROR_FEIGN_CLIENT;
            return new CustomException(errorFeinClient, errorFeinClient.getMessage() + ": " + response.request().url());
        };
    }

    @Bean
    public Retryer retryer() {
        // 1초를 시작으로 최대 1초 간격으로, 최대 3번 재시도
        // 최초 1초이고, 그 이후는 1.5를 곱하면서 3번 재시도
        return new Default(1000, 1000, 3);
    }

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return requestTemplate ->
                requestTemplate.header(MdcType.TRACE_ID.getCode(), Optional.ofNullable(MDC.get(MdcType.TRACE_ID.name())).orElse(""));
    }

}
