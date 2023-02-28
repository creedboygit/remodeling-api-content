package com.hanssem.remodeling.content.api.service.display.dto;

import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentWithDataResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(title = "DisplayMallResponse", description = "전시 컴포넌트 목록 조회(몰)")
@Getter
@Setter
@ToString
public final class DisplayInfoDto implements Serializable {

    @Schema(title = "templateKey", description = "전시템플릿번호", type = "string", example = "152")
    private String dispTemplateNo;

    @Schema(title = "templateApiCd", description = "전시템플릿API코드", type = "string", example = "M_MAIN")
    private String templateApiCd;

    private List<DisplayComponentWithDataResponse> dispComponentList = new ArrayList<>();


}
