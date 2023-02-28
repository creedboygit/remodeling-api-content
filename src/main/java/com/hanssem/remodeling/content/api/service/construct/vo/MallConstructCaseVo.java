package com.hanssem.remodeling.content.api.service.construct.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MallConstructCaseVo {

    private String code;

    private String message;

    private PagingMapResponse pagingMap;

    private List<ContentsListResponse> contentsList = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class PagingMapResponse {

        @JsonProperty("curPage")
        private String curPage;

        @JsonProperty("totalRecordCount")
        private String totalRecordCount;

        @JsonProperty("pageCount")
        private String pageCount;

        @JsonProperty("pageRow")
        private String pageRow;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class ContentsListResponse {

        @JsonProperty("seq")
        private String seq;

        @JsonProperty("imgUrl")
        private String imgUrl;

        @JsonProperty("area1")
        private String area1;

        @JsonProperty("area2")
        private String area2;

        @JsonProperty("title")
        private String title;

        @JsonProperty("detailUrl")
        private String detailUrl;

        @JsonProperty("wishYn")
        private String wishYn;

        @JsonProperty("newYN")
        private String newYN;

        @JsonProperty("shopNm")
        private String shopNm;

        @JsonProperty("shopImgUrl")
        private String shopImgUrl;

        @JsonProperty("addrAptNm")
        private String addrAptNm;

        @JsonProperty("sidoSggNm")
        private String sidoSggNm;

        @JsonProperty("contentsTypeCd")
        private String contentsTypeCd;

        @JsonProperty("vrUrl")
        private String vrUrl;
    }
}
