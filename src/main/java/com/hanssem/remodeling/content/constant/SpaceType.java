package com.hanssem.remodeling.content.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum SpaceType {
    COMMON("COMMON", "공통"),
    LIVING_ROOM("LIVING_ROOM", "거실"),
    KITCHEN("KITCHEN", "부엌"),
    ENTRANCE("ENTRANCE", "현관"),
    BATH("BATH", "욕실"),
    ROOM("ROOM", "방");

    private String code;
    private String name;

    SpaceType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    private static final Map<String, SpaceType> BY_CODE =
        Stream.of(values()).collect(Collectors.toMap(SpaceType::getCode, Function.identity()));

    private static final Map<String, SpaceType> BY_NAME =
        Stream.of(values()).collect(Collectors.toMap(SpaceType::getName, Function.identity()));

    public static SpaceType valueOfCode(String code) {
        return BY_CODE.get(code);
    }

    public static SpaceType valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
