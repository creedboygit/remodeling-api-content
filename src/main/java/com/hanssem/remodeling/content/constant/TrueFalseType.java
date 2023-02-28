package com.hanssem.remodeling.content.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum TrueFalseType {
    TRUE("true", "TRUE", "True"),
    FALSE("false", "FALSE", "False");

    private final String lowerCase;
    private final String upperCase;
    private final String pascalCase;
}
