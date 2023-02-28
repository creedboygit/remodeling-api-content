package com.hanssem.remodeling.content.api.controller.goods.response;

import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Schema(title = "GoodsKeywordRecommendationTagResponse", description = "상품 추천태그리스트 응답객체")
public class GoodsKeywordRecommendationTagResponse implements Serializable {

    @Schema(title = "keywordName", description = "키워드명", example = "벽면지")
    private String keywordName;

    public static GoodsKeywordRecommendationTagResponse of(String keywordName) {
        return new GoodsKeywordRecommendationTagResponse(keywordName);
    }
}
