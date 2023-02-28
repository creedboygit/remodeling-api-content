package com.hanssem.remodeling.content.api.controller.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DotcomCategoryListResponse", description = "닷컴 카테고리 리스트 응답객체")
public class DotcomCategoryListResponse implements Serializable {

    @Schema(title = "categoryList", description = "categoryList", example = "")
    private List<DotcomCategoryListDetailResponse> categoryList;

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(title = "DotcomCategoryListDetailResponse", description = "닷컴 카테고리 리스트 응답객체 상세")
    public static class DotcomCategoryListDetailResponse implements Serializable {

        @Schema(title = "ctgNo", description = "카테고리번호", example = "16129")
        int ctgNo;

        @Schema(title = "ctgNm", description = "카테고리명", example = "부엌")
        String ctgNm;

        @Schema(title = "ctgLevel", description = "카테고리레벨", example = "3")
        int ctgLevel;

        @Schema(title = "ctgNoUp", description = "상위카테고리번호", example = "16139")
        int ctgNoUp;

        @Schema(title = "dspOrder", description = "진열순서", example = "1")
        int dspOrder;

        @Schema(title = "pathCtgNo", description = "카테고리패스번호", example = "\\\\13465\\\\16139\\\\16141")
        String pathCtgNo;

        @Schema(title = "pathCtgNm", description = "카테고리패스명", example = "\\\\한샘닷컴\\\\리모델링 상품\\\\부엌")
        String pathCtgNm;

        @Schema(title = "imgUrlPc", description = "PC이미지URL", example = "")
        String imgUrlPc;

        @Schema(title = "imgOverUrlPc", description = "PC이미지URL", example = "")
        String imgOverUrlPc;

        @Schema(title = "imgUrlMobile", description = "모바일이미지URL", example = "")
        String imgUrlMobile;

        @Schema(title = "imgOverUrlMobile", description = "모바일이미지URL", example = "")
        String imgOverUrlMobile;

        @Schema(title = "imgCtgrURL", description = "카테고리이미지URL", example = "")
        String imgCtgrURL;
    }
}
