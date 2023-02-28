package com.hanssem.remodeling.content.api.controller.goods.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "CategoryGoodsResponse", description = "카테고리 응답 객체")
public class CategoryGoodsResponse {

    CategoryInfoResponse category;
}
