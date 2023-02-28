package com.hanssem.remodeling.content.api.controller.goods.response;

import com.hanssem.remodeling.content.common.model.IsYn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "GoodsScrapResponse", description = "상품 스크랩 여부 객체")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsScrapResponse {

    @Schema(title = "scrapYn", description = "스크랩 여부", example = "true")
    private String scrapYn;

    public GoodsScrapResponse(boolean scrapYn) {

        this.scrapYn = scrapYn ? IsYn.Y.name() : IsYn.N.name();
    }
}
