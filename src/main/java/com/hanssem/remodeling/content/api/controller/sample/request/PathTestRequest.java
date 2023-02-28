package com.hanssem.remodeling.content.api.controller.sample.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "PathTestRequest", description = "회원 생성용 데이터")
@Data
@ParameterObject
public final class PathTestRequest {

    @NotBlank(message = "값을 입력해주세요.")
    @Schema(name = "pathData", title = "도메인 붙기전 데이터", type = "string", required = true, example = "/img-test/my_profile.png")
    private String pathData;

}
