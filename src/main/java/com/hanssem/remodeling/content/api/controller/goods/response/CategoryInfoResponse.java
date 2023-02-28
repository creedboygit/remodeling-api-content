package com.hanssem.remodeling.content.api.controller.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "CategoryInfoResponse", description = "카테고리 정보 응답객체")
public class CategoryInfoResponse implements Serializable {

    @Schema(title = "categoryLevel", description = "카테고리레벨", example = "3")
    private int categoryLevel;

    @Schema(title = "categoryNo", description = "카테고리NO", example = "16223")
    private int categoryNo;

    @Schema(title = "categoryName", description = "카테고리명", example = "부엌")
    private String categoryName;

    @Builder.Default
    private List<GoodsListResponse> goods = new ArrayList<>();
}
