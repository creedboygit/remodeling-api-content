package com.hanssem.remodeling.content.common.error;

import com.hanssem.remodeling.content.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomException extends RuntimeException{

    private int code;
    private String systemMessage;
    private Map<String, String> data;

    public CustomException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomException(int code, String msg, final String systemMessage) {
        super(msg);
        this.code = code;
        setSystemMessage(systemMessage);
    }

    public CustomException(int code, String msg, final Map<String, String> data) {
        super(msg);
        this.code = code;
        this.data = data;
    }

    public CustomException(final int code, final String message, final String systemMessage, final Map<String, String> data) {
        super(message);
        this.code = code;
        this.data = data;
        setSystemMessage(systemMessage);
    }

    public CustomException(ResponseCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public CustomException(ResponseCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
        this.systemMessage = errorCode.getMessage();
    }

    public CustomException(ResponseCode errorCode, String msg, final String systemMessage) {
        this(errorCode, msg);
        setSystemMessage(systemMessage);
    }

    public CustomException(ResponseCode errorCode, Map<String, String> data) {
        this(errorCode.getCode(), errorCode.getMessage(), "", data);
    }

    private void setSystemMessage(final String systemMessage) {
        if (StringUtils.isNotBlank(systemMessage)) {
            this.systemMessage = systemMessage;
        }
    }
}
