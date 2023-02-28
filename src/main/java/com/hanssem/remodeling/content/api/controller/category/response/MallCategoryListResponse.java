package com.hanssem.remodeling.content.api.controller.category.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MallCategoryListResponse", description = "한샘몰 카테고리 리스트 응답객체")
public class MallCategoryListResponse implements Serializable {

    @Schema(title = "categoryList", description = "categoryList", example = "")
    private List<MallCategoryListDetailInfoResponse> categoryList;
}
