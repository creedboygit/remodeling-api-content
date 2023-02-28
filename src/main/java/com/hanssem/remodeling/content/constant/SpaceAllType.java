package com.hanssem.remodeling.content.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum SpaceAllType {
    COMMON("COMMON", "공통"),
    LIVING_ROOM("LIVING_ROOM", "거실"),
    KITCHEN("KITCHEN", "부엌"),
    ENTRANCE("ENTRANCE", "현관"),
    BATH("BATH", "욕실"),
    ROOM("ROOM", "방"),
    PUBLIC_BATH("PUBLIC_BATH", "공용욕실"),
    SMALL_BATH("SMALL_BATH", "소형욕실"),
    BED_ROOM("BED_ROOM", "침실"),
    STUDY_ROOM("STUDY_ROOM", "서재"),
    CHILD_ROOM("CHILD_ROOM", "자녀방"),
    DRESS_ROOM("DRESS_ROOM", "드레스룸"),
    UTILITY_ROOM("UTILITY_ROOM", "다용도");

    private String code;
    private String name;

    SpaceAllType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    private static final Map<String, SpaceAllType> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(SpaceAllType::getCode, Function.identity()));

    private static final Map<String, SpaceAllType> BY_NAME =
            Stream.of(values()).collect(Collectors.toMap(SpaceAllType::getName, Function.identity()));

    public static SpaceAllType valueOfCode(String code) {
        return BY_CODE.get(code);
    }

    public static SpaceAllType valueOfName(String name) {
        return BY_NAME.get(name);
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
