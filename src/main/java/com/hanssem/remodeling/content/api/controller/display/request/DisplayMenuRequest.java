package com.hanssem.remodeling.content.api.controller.display.request;

import com.hanssem.remodeling.content.constant.DisplayScreenType;
import com.hanssem.remodeling.content.constant.HsMallAllType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "DisplayMenuRequest", description = "전시 메뉴 조회")
@Getter
@Setter
@ToString
@ParameterObject
public class DisplayMenuRequest {

    @Schema(title = "typeValue", description = "Mobile / PC", type = "string", example = "MOBILE", implementation = DisplayScreenType.class, requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "typeValue 은(는) 필수 입니다.")
    private DisplayScreenType typeValue;


    @Schema(title = "viewType", description = "viewType", type = "string", example = "REMODELING", implementation = HsMallAllType.class, requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "viewType 은(는) 필수 입니다.")
    private HsMallAllType viewType;

}
