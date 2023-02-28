package com.hanssem.remodeling.content.api.controller.budget.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(title = "StyleMaterialResponse", description = "공통공사/공간공사 상품 조회")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class StyleMaterialResponse {

    @Schema(name = "constructionField", title = "공사영역", required = true, example = "목공")
    private String constructionField;

    @Schema(name = "materialName", title = "자재명", required = true, example = "목공상품명")
    private String materialName;

    @Schema(name = "totalAmount", title = "금액", required = true, example = "1500000")
    private int totalAmount;
}
