package com.hanssem.remodeling.content.api.controller.budget.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "UserEstimateListResponse", description = "사용자 담은공간 견적")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class UsersEstimateResponse implements Serializable {

    @Schema(name = "estimateMId", title = "예산견적마스터ID", type = "Long", required = true, example = "1")
    private Long estimateMId;

    @Schema(name = "userId", title = "사용자ID", type = "Long", required = true, example = "89")
    private Long userId;

    @Schema(name = "ptypValue", title = "평형값", type = "Integer", required = true, example = "20")
    private int ptypValue;

    @Schema(name = "roomCount", title = "방(개수)", type = "Integer", required = true, example = "2")
    private int roomCount;

    @Schema(name = "bathCount", title = "욕실(개수)", type = "Integer", required = true, example = "1")
    private int bathCount;

    @Schema(name = "images", title = "견적서 이미지 URL", required = true, example =
            "[\"https://dev-static.remodeling.hanssem.com/content/budget/style/living.jpg\",\n" +
                    "        \"https://dev-static.remodeling.hanssem.com/content/budget/style/kitchen.jpg\",\n" +
                    "        \"https://dev-static.remodeling.hanssem.com/content/budget/style/entrance.jpg\"]")
    private List<String> images;

    @Schema(name = "totalEstimateAmount", title = "총견적금액", type = "Integer", required = true, example = "10000000")
    private int totalEstimateAmount;

    @Schema(name = "consultYn", title = "상담신청여부", type = "String", required = true, example = "Y")
    private String consultYn;
}
