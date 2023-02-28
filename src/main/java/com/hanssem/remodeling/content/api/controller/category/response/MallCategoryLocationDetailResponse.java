package com.hanssem.remodeling.content.api.controller.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MallCategoryLocationMapResponse", description = "한샘몰 카테고리 현재위치 응답객체 상세")
public class MallCategoryLocationDetailResponse implements Serializable {

    @Schema(title = "categoryNo", description = "카테고리번호", example = "16129")
    int categoryNo;

    @Schema(title = "categoryName", description = "카테고리명", example = "자녀방 / 서재")
    String categoryName;

    @Schema(title = "categoryNoLarge", description = "대카테고리번호", example = "16110")
    int categoryNoLarge;

    @Schema(title = "categoryNoMiddle", description = "중카테고리번호", example = "16110")
    int categoryNoMiddle;

    @Schema(title = "categoryNoSmall", description = "소카테고리번호", example = "16110")
    int categoryNoSmall;

    @Schema(title = "categoryNoTiny", description = "세카테고리번호", example = "16110")
    int categoryNoTiny;

    @Schema(title = "categoryNameLarge", description = "대카테고리명", example = "빌트인수납")
    String categoryNameLarge;

    @Schema(title = "categoryNameMiddle", description = "중카테고리명", example = "빌트인수납")
    String categoryNameMiddle;

    @Schema(title = "categoryNameSmall", description = "소카테고리명", example = "빌트인수납")
    String categoryNameSmall;

    @Schema(title = "categoryNameTiny", description = "세카테고리명", example = "빌트인수납")
    String categoryNameTiny;

    @Schema(title = "categoryLevel", description = "카테고리레벨", example = "6")
    int categoryLevel;
}
