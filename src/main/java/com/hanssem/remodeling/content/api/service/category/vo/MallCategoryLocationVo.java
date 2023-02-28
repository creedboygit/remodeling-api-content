package com.hanssem.remodeling.content.api.service.category.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MallCategoryLocationVo {

    private String code;
    private String message;

    private MallCateLocationMapApiResponse cateLocationMap;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MallCateLocationMapApiResponse {

        @JsonProperty("CtgNo")
        private int categoryNo;

        @JsonProperty("CtgNm")
        private String categoryName;

        @JsonProperty("CtgNo_L")
        private int categoryNoLarge;

        @JsonProperty("CtgNo_M")
        private int categoryNoMiddle;

        @JsonProperty("CtgNo_S")
        private int categoryNoSmall;

        @JsonProperty("CtgNo_D")
        private int categoryNoTiny;

        @JsonProperty("CtgNm_L")
        private String categoryNameLarge;

        @JsonProperty("CtgNm_M")
        private String categoryNameMiddle;

        @JsonProperty("CtgNm_S")
        private String categoryNameSmall;

        @JsonProperty("CtgNm_D")
        private String categoryNameTiny;

        @JsonProperty("CtgLevel")
        private int categoryLevel;
    }
}
