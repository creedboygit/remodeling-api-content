package com.hanssem.remodeling.content.api.controller.budget.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(title = "SaveEstimateRequest", description = "예산감잡기 견적 저장 객체")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SaveEstimateRequest {

    @NotNull(message = "값을 입력해주세요.")
    @Schema(title = "ptypValue", type = "int", required = true, example = "30")
    private int ptypValue;

//    @NotNull(message = "값을 입력해주세요.")
//    @Schema(title = "userId", type = "Long", required = true, example = "89")
//    private Long userId;

    @NotNull(message = "값을 입력해주세요.")
    @Schema(title = "roomCount", type = "int", required = true, example = "3")
    private int roomCount;

    @NotNull(message = "값을 입력해주세요.")
    @Schema(title = "bathCount", type = "int", required = true, example = "2")
    private int bathCount;

    @NotNull(message = "값을 입력해주세요.")
    @Schema(title = "totalEstimateAmount", type = "int", required = true, example = "10000000")
    private int totalEstimateAmount;

    private List<EstimateInfo> estimateInfoList = new ArrayList<>();

    @Schema(title = "EstimateInfo", description = "견적상세리스트")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class EstimateInfo {

        @Schema(name = "styleMId", title = "스타일아이디", type = "Long", example = "4")
        @NotBlank
        private Long styleMId;
    }
}
