package com.hanssem.remodeling.content.admin.controller.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "CategoryResponse", description = "카테고리 정보 응답객체")
public class CategoryResponse implements Serializable {

    @Schema(title = "id", description = "상품카테고리ID", example = "378")
    private Long id;

    @Schema(title = "categoryNo", description = "카테고리NO", example = "16223")
    private int categoryNo;

    @Schema(title = "categoryLargeNo", description = "카테고리대분류NO", example = "16139")
    private int categoryLargeNo;

    @Schema(title = "categoryLargeName", description = "카테고리대분류명", example = "16139")
    private String categoryLargeName;

    @Schema(title = "categoryMiddleNo", description = "카테고리중분류NO", example = "16144")
    private int categoryMiddleNo;

    @Schema(title = "categoryMiddleName", description = "카테고리중분류명", example = "16139")
    private String categoryMiddleName;

    @Schema(title = "categorySmallNo", description = "카테고리소분류NO", example = "16191")
    private int categorySmallNo;

    @Schema(title = "categorySmallName", description = "카테고리소분류명", example = "16139")
    private String categorySmallName;

    @Schema(title = "categoryTinyNo", description = "카테고리세분류NO", example = "16223")
    private int categoryTinyNo;

    @Schema(title = "categoryTinyName", description = "카테고리세분류명", example = "16139")
    private String categoryTinyName;

    @Schema(title = "categoryDisplaySeq", description = "카테고리디스플레이순번", example = "1")
    private int categoryDisplaySeq;

}
