package com.hanssem.remodeling.content.api.controller.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(title = "GoodsKeywordSearchResponse", description = "상품 키워드리스트 응답객체")
public class GoodsKeywordSearchResponse implements Serializable {

    @Schema(title = "keywordSeq", description = "키워드순번", example = "1")
    private int keywordSeq;

    @Schema(title = "keywordName", description = "키워드명", example = "클래식")
    private String keywordName;
}
