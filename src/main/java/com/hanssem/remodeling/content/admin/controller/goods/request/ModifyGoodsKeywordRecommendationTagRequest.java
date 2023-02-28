package com.hanssem.remodeling.content.admin.controller.goods.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ModifyGoodsKeywordRecommendationTagRequest", description = "상품 추천태그리스트 수정 객체")
public class ModifyGoodsKeywordRecommendationTagRequest implements Serializable {
    @Schema(title = "id", description = "키워드 ID", example = "1")
    private Long id;

    @Schema(title = "keywordSeq", description = "키워드순번", example = "1")
    private int keywordSeq;

    @Schema(title = "keywordName", description = "키워드명", example = "벽면지")
    private String keywordName;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ModifyGoodsKeywordRecommendationTagRequest) {
            ModifyGoodsKeywordRecommendationTagRequest request = (ModifyGoodsKeywordRecommendationTagRequest) obj;
            return request.keywordName.equals(((ModifyGoodsKeywordRecommendationTagRequest) obj).getKeywordName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return keywordName.hashCode();
    }
}
