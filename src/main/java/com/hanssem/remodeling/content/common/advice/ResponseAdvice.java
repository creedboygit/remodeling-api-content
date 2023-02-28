package com.hanssem.remodeling.content.common.advice;

import com.hanssem.remodeling.content.common.response.Response;
import com.hanssem.remodeling.content.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.hanssem.remodeling.content.api.controller",
        "com.hanssem.remodeling.content.admin.controller"})
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (body instanceof Exception) {
            final Exception e = (Exception) body;
            log.error("exception => {}", e.getMessage());
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.toString(), null);
        } else if (body instanceof Resource || body instanceof String || body instanceof byte[]) {
            return body;
        } else {
            return new Response(HttpStatus.OK.value(), ResponseCode.OK.getMessage(), body);
        }
    }
}
