package com.hanssem.remodeling.content.api.controller.event.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "DisplayGnbRequest", description = "전시 GNB 조회")
@Getter
@Setter
@ToString
public class EventDetailResponse {


    List<Object> componentList;
    EventDetail eventDetail;
    EventTemplate eventTemplate;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class EventDetail {

        @Schema(title = "shpNo", description = "shpNo", type = "integer", example = "1")
        private int shpNo;
        @Schema(title = "shpNm", description = "기획전이름", type = "string", example = "한샘닷컴 기획전 테스트")
        private String shpNm;
        @Schema(title = "shpTypCd", description = "shpTypCd", type = "string", example = "08")
        private String shpTypCd;
        @Schema(title = "shpConts", description = "기획전설명", type = "string", example = "한샘닷컴 기획전 테스트")
        private String shpConts;
        @Schema(title = "keyword", description = "분류검색어", type = "string", example = "샘키즈1,샘키즈2,샘키즈3")
        private String keyword;
        @Schema(title = "layoutDispYn", description = "layoutDispYn", type = "integer", example = "1")
        private int layoutDispYn;
        @Schema(title = "horizontalType1BannerUrl", description = "배너이미지1", type = "string", example = "//devimage.hanssem.com/hsimg/plan/720/15/15832_P1.jpg")
        private String horizontalType1BannerUrl;
        @Schema(title = "horizontalType2BannerUrl", description = "배너이미지2", type = "string", example = "//devimage.hanssem.com/hsimg/plan/720/15/15832_P2.jpg")
        private String horizontalType2BannerUrl;
        /*
        @Schema(title = "horizontalType3BannerUrl", description = "horizontalType3BannerUrl", type = "string", example = "1")
        private String horizontalType3BannerUrl;
        @Schema(title = "squareBannerUrl", description = "squareBannerUrl", type = "string", example = "")
        private String squareBannerUrl;
        */
        @Schema(title = "shpTypEventCtg", description = "shpTypEventCtg", type = "string", example = "RM,KCBR,FN,HG,HC")
        private String shpTypEventCtg;
        @Schema(title = "counselCd", description = "상담신청코드: C(일반상담)/V(방문상담)", type = "string", example = "")
        private String counselCd;
        @Schema(title = "formIdx", description = "formIdx", type = "integer", example = "0")
        private int formIdx;
        @Schema(title = "hcCate", description = "hcCate", type = "string", example = "N")
        private String hcCate;
        @Schema(title = "myFavorite", description = "myFavorite", type = "string", example = "N")
        private String myFavorite;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EventTemplate {

        @Schema(title = "dispTemplateNo", description = "dispTemplateNo", type = "string", example = "22850")
        private String dispTemplateNo;
        @Schema(title = "dispTemplateNm", description = "dispTemplateNm", type = "string", example = "기획전용 전시 탬플릿 15825")
        private String dispTemplateNm;
        @Schema(title = "dispTemplateType", description = "dispTemplateType", type = "string", example = "05")
        private String dispTemplateType;
        @Schema(title = "dispTemplateBgnYmdt", description = "dispTemplateBgnYmdt", type = "string", example = "2022-11-01")
        private LocalDate dispTemplateBgnYmdt;
        @Schema(title = "dispTemplateEndYmdt", description = "dispTemplateEndYmdt", type = "string", example = "2023-01-31")
        private LocalDate dispTemplateEndYmdt;
    }

}
