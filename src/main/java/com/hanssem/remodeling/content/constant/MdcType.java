package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MdcType {
    TRACE_ID("X-B3-TraceId"),
    REQUEST_URL(""),
    CUST_NO("custNo");

    private final String code;
}
