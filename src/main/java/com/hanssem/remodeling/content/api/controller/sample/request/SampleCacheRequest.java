package com.hanssem.remodeling.content.api.controller.sample.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "SameCacheRequest", description = "")
@Data
@ParameterObject
public class SampleCacheRequest {

    @Schema(title = "reqParam1", type = "string", example = "reqParam1")
    private String reqParam1;

    @Schema(title = "reqParam2", type = "integer", example = "2")
    private int reqParam2;
}
