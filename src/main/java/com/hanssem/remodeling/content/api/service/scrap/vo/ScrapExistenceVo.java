package com.hanssem.remodeling.content.api.service.scrap.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ScrapExistenceVo {

    private String code;
    private String message;
    private boolean data;

    public ScrapExistenceVo(String code, String message, boolean data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
