package com.hanssem.remodeling.content.api.controller.display.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Schema(title = "DisplayComponentGdsResponse", description = "상품 목록 사이에 삽입되는 전시 컴포넌트, 필수값 아님")
@Getter
@Setter
@ToString
public class DisplayComponentGdsResponse {

    @JsonProperty("rNum")
    private int rNum;
    private String adType;
    private String dispComponentType;
    private String isAuto;
    private int adNo;
    private int dispTemplateNo;
    private int dispComponentNo;
    private String ctgNo;
    private String cdGrpNo;
    private String componentLayoutCd;
    private String gdsCnt;
}
