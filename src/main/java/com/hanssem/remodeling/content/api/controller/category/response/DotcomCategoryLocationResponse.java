package com.hanssem.remodeling.content.api.controller.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DotcomCategoryLocationResponse", description = "한샘닷컴 카테고리 현재위치 응답객체")
public class DotcomCategoryLocationResponse implements Serializable {

    @Schema(title = "categoryLocationMap", description = "categoryLocationMap", example = "")
    private DotcomCategoryLocationMapResponse cateLocationMap;

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(title = "DotcomCategoryLocationMapResponse", description = "한샘닷컴 카테고리 현재위치 응답객체 상세")
    public static class DotcomCategoryLocationMapResponse implements Serializable {

        @Schema(title = "ctgNo", description = "카테고리번호", example = "16129")
        int ctgNo;

        @Schema(title = "ctgNm", description = "카테고리명", example = "자녀방 / 서재")
        String ctgNm;

        @Schema(title = "ctgNoL", description = "대카테고리번호", example = "16110")
        int ctgNoL;

        @Schema(title = "ctgNoM", description = "중카테고리번호", example = "16110")
        int ctgNoM;

        @Schema(title = "ctgNoS", description = "소카테고리번호", example = "16110")
        int ctgNoS;

        @Schema(title = "ctgNoD", description = "세카테고리번호", example = "16110")
        int ctgNoD;

        @Schema(title = "ctgNmL", description = "대카테고리명", example = "빌트인수납")
        String ctgNmL;

        @Schema(title = "ctgNmM", description = "중카테고리명", example = "빌트인수납")
        String ctgNmM;

        @Schema(title = "ctgNmS", description = "소카테고리명", example = "빌트인수납")
        String ctgNmS;

        @Schema(title = "ctgNmD", description = "세카테고리명", example = "빌트인수납")
        String ctgNmD;

        @Schema(title = "ctgLevel", description = "카테고리레벨", example = "6")
        int ctgLevel;
    }
}
