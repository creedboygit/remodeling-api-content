package com.hanssem.remodeling.content.api.controller.event.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "DisplayGnbRequest", description = "전시 GNB 조회")
@Getter
@Setter
@ToString
public class EventResponse {


    @Schema(title = "rNum", description = "rNum", type = "integer", example = "1")
    @JsonProperty("rNum")
    private int rNum;
    @Schema(title = "shpNo", description = "shpNo", type = "string", example = "15832")
    private int shpNo;
    @Schema(title = "shpNm", description = "기획점이름", type = "string", example = "테스트_기획전_1")
    private String shpNm;
    @Schema(title = "shpConts", description = "기획전설명", type = "string", example = "테스트_기획전_1_설명")
    private String shpConts;
    @Schema(title = "dispBgnDt", description = "전시시작일", type = "string", example = "2022-11-21")
    private LocalDate dispBgnDt;
    @Schema(title = "dispEndDt", description = "전시종료일", type = "string", example = "2023-01-31")
    private LocalDate dispEndDt;
    @Schema(title = "keyword", description = "분류검색어", type = "string", example = "테스트1_기획전_분류_1,테스트1_기획전_분류_2,테스트1_기획전_분류_3")
    private String keyword;
    @Schema(title = "counselCd", description = "상담신청코드: C(일반상담)/V(방문상담)", type = "string", example = "C")
    private String counselCd;
    @Schema(title = "horizontalType1BannerUrl", description = "배너이미지1", type = "string", example = "https://devimage.hanssem.com/hsimg/plan/720/15/15832_P2.jpeg")
    private String horizontalType1BannerUrl;
    @Schema(title = "horizontalType2BannerUrl", description = "배너이미지2", type = "string", example = "")
    private String horizontalType2BannerUrl;

    /* 리모델링은 이하 스펙 아웃. 어드민에 추가될 경우 추가.
    @Schema(title = "horizontalType3BannerUrl", description = "horizontalType3BannerUrl", type = "string", example = "")
    private String horizontalType3BannerUrl;
    @Schema(title = "squareBannerUrl", description = "squareBannerUrl", type = "STRING", example = "")
    private String squareBannerUrl;
     */

}
