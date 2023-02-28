package com.hanssem.remodeling.content.api.controller.category.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MallCategoryLocationResponse", description = "한샘몰 카테고리 현재위치 응답객체")
public class MallCategoryLocationResponse implements Serializable {

    private MallCategoryLocationDetailResponse categoryLocationMap;
}
