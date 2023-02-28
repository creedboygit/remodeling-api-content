package com.hanssem.remodeling.content.admin.controller.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "GoodsKeywordStyleResponse", description = "스타일코드 응답객체")
public class GoodsKeywordStyleResponse {
    @Schema(title = "id", description = "키워드 ID", example = "1")
    private Long id;

    @Schema(title = "keywordSeq", description = "키워드순번", example = "1")
    private int keywordSeq;

    @Schema(title = "keywordName", description = "키워드명", example = "벽면지")
    private String keywordName;
}
