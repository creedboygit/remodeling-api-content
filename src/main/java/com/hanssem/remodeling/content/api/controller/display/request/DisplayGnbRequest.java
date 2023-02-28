package com.hanssem.remodeling.content.api.controller.display.request;

import com.hanssem.remodeling.content.constant.DisplayScreenType;
import com.hanssem.remodeling.content.constant.HsMallType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "DisplayGnbRequest", description = "전시 GNB 조회")
@Getter
@Setter
@ToString
@ParameterObject
public class DisplayGnbRequest {

    @NotNull(message = "viewType 은(는) 필수 입니다.")
    @Schema(title = "viewType", description = "viewType", type = "string", example = "REMODELING", implementation = HsMallType.class, requiredMode = RequiredMode.REQUIRED)
    private HsMallType viewType;

    @NotNull(message = "typeValue 은(는) 필수 입니다.")
    @Schema(title = "typeValue", description = "Mobile / PC", type = "string", example = "MOBILE", implementation = DisplayScreenType.class, requiredMode = RequiredMode.REQUIRED)
    private DisplayScreenType typeValue;

}
