package com.hanssem.remodeling.content.api.service.category.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MallCategoryListVo {

    private String code;
    private String message;

    private List<MallCategoryListDetailApiResponse> categoryList = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MallCategoryListDetailApiResponse {

        @JsonProperty("CtgNo")
        private int categoryNo;

        @JsonProperty("CtgNm")
        private String categoryName;

        @JsonProperty("CtgLevel")
        private int categoryLevel;

        @JsonProperty("CtgNo_UP")
        private int categoryNoUp;

        @JsonProperty("DspOrder")
        private int displayOrder;

        @JsonProperty("Path_CtgNo")
        private String pathCategoryNo;

        @JsonProperty("Path_CtgNm")
        private String pathCategoryName;

        @JsonProperty("ImgUrl_PC")
        private String imageUrlPc;

        @JsonProperty("ImgOverUrl_PC")
        private String imageOverUrlPc;

        @JsonProperty("ImgUrl_Mobile")
        private String imageUrlMobile;

        @JsonProperty("ImgOverUrl_Mobile")
        private String imageOverUrlMobile;

        @JsonProperty("ImgCtgrURL")
        private String imageCategoryURL;
    }
}
