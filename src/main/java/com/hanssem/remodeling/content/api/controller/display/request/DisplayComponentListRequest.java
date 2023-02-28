package com.hanssem.remodeling.content.api.controller.display.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "DisplayComponentListRequest", description = "전시 컴포넌트 목록 조회")
@Getter
@Setter
@ToString
@ParameterObject
public class DisplayComponentListRequest {

    @Schema(title = "dispTemplateNo", description = "dispTemplateNo or templateApiCd 중 하나 필수.", type = "string", example = "343")
    private String dispTemplateNo;

    @Schema(title = "templateApiCd", description = "전시템플릿API코드(리모델링 전시에는 적용하지 않음) dispTemplateNo or templateApiCd 중 하나 필수.", type = "string", example = "")
    private String templateApiCd;

    @Schema(title = "ctgNo", type = "string", example = "")
    private String ctgNo;

}
