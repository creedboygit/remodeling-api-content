package com.hanssem.remodeling.content.api.service.category.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DotcomCategoryLocationVo {

    private String code;
    private String message;

    private DotcomCateLocationMapApiResponse cateLocationMap;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DotcomCateLocationMapApiResponse {

        @JsonProperty("CtgNo")
        private int ctgNo;

        @JsonProperty("CtgNm")
        private String ctgNm;

        @JsonProperty("CtgNo_L")
        private int ctgNoL;

        @JsonProperty("CtgNo_M")
        private int ctgNoM;

        @JsonProperty("CtgNo_S")
        private int ctgNoS;

        @JsonProperty("CtgNo_D")
        private int ctgNoD;

        @JsonProperty("CtgNm_L")
        private String ctgNmL;

        @JsonProperty("CtgNm_M")
        private String ctgNmM;

        @JsonProperty("CtgNm_S")
        private String ctgNmS;

        @JsonProperty("CtgNm_D")
        private String ctgNmD;

        @JsonProperty("CtgLevel")
        private int ctgLevel;
    }
}
