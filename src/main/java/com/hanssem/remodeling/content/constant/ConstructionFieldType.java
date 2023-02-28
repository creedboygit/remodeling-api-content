package com.hanssem.remodeling.content.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ConstructionFieldType {
    PAPERING("PAPERING","도배"),
    DOOR("DOOR", "도어"),
    SKIRTING("SKIRTING","걸레받이"),
    MOLDING("MOLDING","몰딩"),
    WINDOWS("WINDOWS","창호"),
    CARPENTRY("CARPENTRY","목공"),
    DEMOLITION("DEMOLITION","철거"),
    ELECTRIC("ELECTRIC","전기"),

    KITCHEN("KITCHEN","부엌"),
    BATH("BATH","욕실"),
    STORAGE("STORAGE","수납"),
    TILE("TILE","타일"),
    CENTRALDOOR("CENTRALDOOR","중문"),
    LIGHTING("LIGHTING","조명"),
    WALL_MATERIAL("WALL_MATERIAL","벽자재"),
    FLOOR("FLOOR","바닥");

    private String code;
    private String name;

    ConstructionFieldType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<String, ConstructionFieldType> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(ConstructionFieldType::getCode, Function.identity()));

    private static final Map<String, ConstructionFieldType> BY_NAME =
            Stream.of(values()).collect(Collectors.toMap(ConstructionFieldType::getName, Function.identity()));

    public static ConstructionFieldType valueOfCode(String code) {
        return BY_CODE.get(code);
    }

    public static ConstructionFieldType valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
