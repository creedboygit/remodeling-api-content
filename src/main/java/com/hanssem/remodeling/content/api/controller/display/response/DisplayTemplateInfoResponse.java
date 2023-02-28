package com.hanssem.remodeling.content.api.controller.display.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisplayTemplateInfoResponse {

    @Schema(title = "key", type = "string", example = "largeCategoryTemplateNo",
            description = "키")
    private String key;

    @Schema(title = "value", description = "값", type = "string", example = "432")
    private String value;

}
