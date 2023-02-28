package com.hanssem.remodeling.content.common.response;

import com.hanssem.remodeling.content.constant.MdcType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Response<T> {

    private int code;
    private String message;
    private String traceId;
    private T data;

    public Response(int status, String message, T data) {
        this.code = status;
        this.message = message;
        this.traceId = MDC.get(MdcType.TRACE_ID.name());
        this.data = data;
    }
}
