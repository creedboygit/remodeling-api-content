package com.hanssem.remodeling.content.api.service.event.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class HsMallEventDetailVo {

    private String code;
    private String message;

    List<Object> componentList;
    PlanDetailMap planDetailMap;
    PlanTemplateMap planTemplateMap;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class PlanDetailMap {

        @JsonProperty("ShpNo")
        private int shpNo;
        @JsonProperty("ShpNm")
        private String shpNm;
        @JsonProperty("ShpTypCd")
        private String shpTypCd;
        @JsonProperty("ShpConts")
        private String shpConts;
        @JsonProperty("Keyword")
        private String keyword;
        @JsonProperty("LayoutDispYN")
        private int layoutDispYn;
        @JsonProperty("HorizontalType1BannerUrl")
        private String horizontalType1BannerUrl;
        @JsonProperty("HorizontalType2BannerUrl")
        private String horizontalType2BannerUrl;
        @JsonProperty("HorizontalType3BannerUrl")
        private String horizontalType3BannerUrl;
        @JsonProperty("SquareBannerUrl")
        private String squareBannerUrl;
        @JsonProperty("ShpTypEventCtg")
        private String shpTypEventCtg;
        @JsonProperty("CounselCd")
        private String counselCd;
        @JsonProperty("FormIdx")
        private int formIdx;
        @JsonProperty("HcCate")
        private String hcCate;
        @JsonProperty("MyFavorite")
        private String myFavorite;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class PlanTemplateMap {

        @JsonProperty("DISPTemplateNo")
        private String dispTemplateNo;
        @JsonProperty("DISPTemplateNm")
        private String dispTemplateNm;
        @JsonProperty("DISPTemplateTYPE")
        private String dispTemplateType;
        @JsonProperty("DISPTemplateBgnYmdt")
        private String dispTemplateBgnYmdt;
        @JsonProperty("DISPTemplateEndYmdt")
        private String dispTemplateEndYmdt;
    }

}
