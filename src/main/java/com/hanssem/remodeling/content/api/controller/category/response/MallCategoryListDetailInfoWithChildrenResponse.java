package com.hanssem.remodeling.content.api.controller.category.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MallCategoryListDetailInfoWithChildrenResponse implements Serializable {

    @Schema(title = "categoryNo", description = "카테고리번호", example = "16129")
    private int categoryNo;

    @Schema(title = "categoryName", description = "카테고리명", example = "부엌")
    private String categoryName;

    @Schema(title = "categoryLevel", description = "카테고리레벨", example = "3")
    private int categoryLevel;

    @Schema(title = "categoryNoUp", description = "상위카테고리", example = "16139")
    private int categoryNoUp;

    @Schema(title = "displayOrder", description = "카테고리진열순서", example = "1")
    private int displayOrder;

    @Schema(title = "pathCategoryNo", description = "카테고리패스", example = "\\\\13465\\\\16139\\\\16141")
    private String pathCategoryNo;

    @Schema(title = "pathCategoryName", description = "카테고리패스명", example = "\\\\한샘닷컴\\\\리모델링 상품\\\\부엌")
    private String pathCategoryName;

    @Schema(title = "imageUrlPc", description = "PC이미지URL", example = "")
    private String imageUrlPc;

    @Schema(title = "imageOverUrlPc", description = "PC Over이미지URL", example = "")
    private String imageOverUrlPc;

    @Schema(title = "imageUrlMobile", description = "모바일 이미지URL", example = "")
    private String imageUrlMobile;

    @Schema(title = "imageOverUrlMobile", description = "모바일 Over이미지URL", example = "")
    private String imageOverUrlMobile;

    @Schema(title = "imageCategoryURL", description = "카테고리이미지URL", example = "")
    private String imageCategoryURL;

    @Schema(title = "displayCategoryTemplateNo", description = "디스플레이템플릿No", example = "432")
    private String displayCategoryTemplateNo;

    @Schema(title = "displayYn", description = "노출여부", example = "Y")
    private String displayYn;

    @Builder.Default
    private List<MallCategoryListDetailInfoWithChildrenResponse> children = new ArrayList<>();
}
