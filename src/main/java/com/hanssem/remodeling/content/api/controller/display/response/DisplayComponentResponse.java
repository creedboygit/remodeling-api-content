package com.hanssem.remodeling.content.api.controller.display.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Schema(title = "DisplayComponentResponse", description = "DisplayComponentResponse")
@Getter
@Setter
@ToString
public class DisplayComponentResponse {

    @JsonProperty("rNum")
    @Schema(title = "rNum", description = "순서", type = "integer", example = "1")
    private int rNum;
    @Schema(title = "adType", description = "전시타입", type = "string", example = "2")
    private String adType;
    @Schema(title = "acctTypeOption", description = "전시타입 세부옵션 depth1", type = "string", example = "")
    private String acctTypeOption;
    @Schema(title = "acctRelTypeOption", description = "전시타입 세부옵션 depth2", type = "string", example = "")
    private String acctRelTypeOption;
    @Schema(title = "dispComponentType", description = "전시타입하위타입", type = "string", example = "DP152_01")
    private String dispComponentType;
    @Schema(title = "isAuto", description = "자동구성여부", type = "string", example = "false")
    private String isAuto;
    @Schema(title = "adNo", description = "전시번호", type = "integer", example = "42101")
    private int adNo;
    @Schema(title = "dispTemplateNo", description = "전시템플릿번호", type = "integer", example = "12978")
    private int dispTemplateNo;
    @Schema(title = "dispComponentNo", description = "전시컴포넌트번호", type = "integer", example = "56038")
    private int dispComponentNo;
    @Schema(title = "ctgNo", description = "카테고리번호", type = "string", example = "")
    private String ctgNo;
    @Schema(title = "cdGrpNo", description = "코드그룹번호", type = "string", example = "DP152")
    private String cdGrpNo;
    @Schema(title = "componentLayoutCd", description = "컴포넌트 레이아웃 코드", type = "string", example = "MAIN_BANNER")
    private String componentLayoutCd;
    @Schema(title = "showTitleYn", description = "전시 타이틀 노출여부", type = "string", example = "1")
    private String showTitleYn;
    @Schema(title = "useSubTitleYn", description = "서브 타이틀 사용여부", type = "string", example = "1")
    private String useSubTitleYn;
    @Schema(title = "useSeeMoreYn", description = "더보기링크 사용여부", type = "string", example = "1")
    private String useSeeMoreYn;
    @Schema(title = "changeSeeMoreTitleYn", description = "더보기 텍스트 변경여부", type = "string", example = "1")
    private String changeSeeMoreTitleYn;
    @Schema(title = "useAutoCompTitleYn", description = "자동구성 타이틀 사용여부", type = "string", example = "")
    private String useAutoCompTitleYn;
    @Schema(title = "subTitle", description = "서브타이틀(전시설명영역)<br>"
            + "> useSubTitleYn이 1일경우", type = "string", example = "")
    private String subTitle;
    @Schema(title = "seeMoreTitle", description = "더보기 텍스트<br>"
            + "> useSeeMoreYn이 1일경우", type = "string", example = "")
    private String seeMoreTitle;
    @Schema(title = "bgColorR", description = "배경색R", type = "string", example = "")
    private String bgColorR;
    @Schema(title = "bgColorG", description = "배경색G", type = "string", example = "")
    private String bgColorG;
    @Schema(title = "bgColorB", description = "배경색B", type = "string", example = "")
    private String bgColorB;

    @Schema(title = "autoConfigData", description = "자동구성데이터<br>"
            + "> content서비스에서 할당.", example = "", type = "object")
    private DisplayAutoConfigData autoConfigData;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisplayAutoConfigData {

        @Schema(title = "url", description = "url", type = "string", example = "/content-service/api/v1/goods?ctgNo=16274")
        String url;
    }
}
