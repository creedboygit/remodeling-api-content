package com.hanssem.remodeling.content.api.controller.budget.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Schema(title = "SaveEstimateResponse", description = "견적 저장 응답 데이터")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class SaveEstimateResponse {

    @Schema(name = "estimateMId", title = "예산견적ID", type = "Long", example = "2")
    @NotBlank
    private Long estimateMId;

    @Schema(name = "userId", title = "사용자아이디", type = "Long", example = "89")
    @NotBlank
    private Long userId;
}
