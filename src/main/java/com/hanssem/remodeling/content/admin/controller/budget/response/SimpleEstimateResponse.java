package com.hanssem.remodeling.content.admin.controller.budget.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "EstimateResponse", description = "선택한 공간 견적 조회")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class SimpleEstimateResponse {

    @Schema(name = "estimateMId", title = "예산견적마스터ID", type = "Long", required = true, example = "1")
    @NotBlank
    private Long estimateMId;

    @Schema(name = "userId", title = "사용자ID", type = "Long", required = true, example = "89")
    @NotBlank
    private Long userId;

    @Schema(name = "ptypValue", title = "평형값", type = "int", required = true, example = "20")
    @NotBlank
    private int ptypValue;

    @Schema(name = "roomCount", title = "방(개수)", type = "int", required = true, example = "2")
    @NotBlank
    private int roomCount;

    @Schema(name = "bathCount", title = "욕실(개수)", type = "int", required = true, example = "1")
    @NotBlank
    private int bathCount;

    @Schema(name = "image", title = "견적서 이미지", required = true, example = "https://dev-static.remodeling.hanssem.com/content/budget/style/estimateImage.jpg")
    @NotBlank
    private String image;

    @Schema(name = "totalEstimateAmount", title = "총견적금액", type = "int", required = true, example = "10000000")
    @NotBlank
    private int totalEstimateAmount;

    @Schema(name = "consultYn", title = "상담신청여부", type = "String", required = true, example = "Y")
    @NotBlank
    private String consultYn;

    public String getImage() {
        return GlobalConstants.makeCdnUrl(image);
    }
}
