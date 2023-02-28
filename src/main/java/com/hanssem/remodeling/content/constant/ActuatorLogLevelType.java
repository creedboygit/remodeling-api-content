package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ActuatorLogLevelType {
    OFF("OFF", "OFF"),
    ERROR("ERROR", "ERROR"),
    WARN("WARN", "WARN"),
    INFO("INFO", "INFO"),
    DEBUG("DEBUG", "DEBUG"),
    TRACE("TRACE", "TRACE");

    private final String code;
    private final String desc;
}
