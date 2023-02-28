package com.hanssem.remodeling.content.api.service.event.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HsMallEventListVo {

    private String code;
    private String message;

    List<EventPlanVo> planList = new ArrayList<>();
    HsMallPagingMapVo pagingMap;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class EventPlanVo {

        @JsonProperty("rnum")
        private int rNum;
        @JsonProperty("ShpNo")
        private int shpNo;
        @JsonProperty("ShpNm")
        private String shpNm;
        @JsonProperty("ShpConts")
        private String shpConts;
        @JsonProperty("DispBgnDt")
        private String dispBgnDt;
        @JsonProperty("DispEndDt")
        private String dispEndDt;
        @JsonProperty("Keyword")
        private String keyword;
        @JsonProperty("CounselCd")
        private String counselCd;
        @JsonProperty("HorizontalType1BannerUrl")
        private String horizontalType1BannerUrl;
        @JsonProperty("HorizontalType2BannerUrl")
        private String horizontalType2BannerUrl;
        @JsonProperty("HorizontalType3BannerUrl")
        private String horizontalType3BannerUrl;
        @JsonProperty("SquareBannerUrl")
        private String squareBannerUrl;
    }

//    @NoArgsConstructor(access = AccessLevel.PRIVATE)
//    @AllArgsConstructor(access = AccessLevel.PRIVATE)
//    @Getter
//    public static class PagingMap {
//
//        private String curPage;
//        private String totalRecordCount;
//        private String pageCount;
//        private String pageRow;
//    }

}
