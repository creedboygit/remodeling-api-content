package com.hanssem.remodeling.content.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ConstructionType {
    BASIC_COMMON("BASIC_COMMON","기본공통공사"),
    COMMON("COMMON","공통공사"),
    SPACE("SPACE","공간공사");

    private String code;
    private String name;

    ConstructionType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<String, ConstructionType> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(ConstructionType::getCode, Function.identity()));

    private static final Map<String, ConstructionType> BY_NAME =
            Stream.of(values()).collect(Collectors.toMap(ConstructionType::getName, Function.identity()));

    public static ConstructionType valueOfCode(String code) {
        return BY_CODE.get(code);
    }

    public static ConstructionType valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
