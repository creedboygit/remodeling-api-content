package com.hanssem.remodeling.content.api.service.category.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DotcomCategoryListVo {

    private String code;
    private String message;

    private List<DotcomCategoryListApiResponse> categoryList = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DotcomCategoryListApiResponse {

        @JsonProperty("CtgNo")
        private int ctgNo;

        @JsonProperty("CtgNm")
        private String ctgNm;

        @JsonProperty("CtgLevel")
        private int ctgLevel;

        @JsonProperty("CtgNo_UP")
        private int ctgNoUp;

        @JsonProperty("DspOrder")
        private int dspOrder;

        @JsonProperty("Path_CtgNo")
        private String pathCtgNo;

        @JsonProperty("Path_CtgNm")
        private String pathCtgNm;

        @JsonProperty("ImgUrl_PC")
        private String imgUrlPc;

        @JsonProperty("ImgOverUrl_PC")
        private String imgOverUrlPc;

        @JsonProperty("ImgUrl_Mobile")
        private String imgUrlMobile;

        @JsonProperty("ImgOverUrl_Mobile")
        private String imgOverUrlMobile;

        @JsonProperty("ImgCtgrURL")
        private String imgCtgrURL;
    }
}
