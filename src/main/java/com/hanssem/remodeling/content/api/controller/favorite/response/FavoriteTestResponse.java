package com.hanssem.remodeling.content.api.controller.favorite.response;

import com.hanssem.remodeling.content.api.controller.goods.response.GoodsSimpleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = "FavoriteTestResponse", description = "인테리어취향테스트 결과 객체")
public class FavoriteTestResponse {

    @Schema(title = "favoriteStyle", description = "취향테스트 스타일정보", type = "FavoriteStyleDto", example = "")
    private FavoriteStyleResponse favoriteStyle;
    @Schema(title = "consultInfo", description = "취향테스트 상담신청정보", type = "ConsultInfo", example = "")
    private ConsultResponse consultResponse;
    @Schema(title = "recommendGoods", description = "추천상품 목록", type = "List<GoodsSimpleResponse>", example = "")
    private List<GoodsSimpleResponse> recommendGoods = new ArrayList<>();
    @Schema(title = "relatedGoods", description = "연관상품 목록", type = "List<GoodsSimpleResponse>", example = "")
    private List<GoodsSimpleResponse> relatedGoods = new ArrayList<>();

}


