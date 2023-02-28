package com.hanssem.remodeling.content.api.controller.display.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "DisplayComponentListResponse", description = "전시 컴포넌트 목록 조회 응답")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DisplayComponentListResponse implements Serializable {

    @Schema(title = "dispTemplateNo", description = "전시템플릿번호", type = "string", example = "152")
    private String dispTemplateNo;

    @Schema(title = "templateApiCd", description = "전시템플릿API코드", type = "string", example = "M_MAIN")
    private String templateApiCd;

    @Builder.Default
    private List<DisplayComponentResponse> dispComponentList = new ArrayList<>();

    @Builder.Default
    private List<DisplayComponentGdsResponse> dispComponentGdsList = new ArrayList();

}
