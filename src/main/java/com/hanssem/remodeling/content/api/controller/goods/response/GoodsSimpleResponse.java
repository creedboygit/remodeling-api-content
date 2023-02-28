package com.hanssem.remodeling.content.api.controller.goods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "GoodsSimpleResponse", description = "상품 목록조회 객체")
@Getter
@Setter
@NoArgsConstructor
public class GoodsSimpleResponse {

    @Schema(title = "id", description = "상품 ID", example = "807838")
    private Long id;

    @Schema(title = "goodsName", description = "상품명", example = "(40평대) 모던 크리스탈 화이트 패키지")
    private String goodsName;

    @Schema(title = "imagePathName", description = "상품 대표 이미지", example = "/gds/550/807/807838_A1.jpg")
    private String imagePathName;

    @Schema(title = "imageUrl", description = "상품 대표 이미지 Url", example = "https://image.hanssem.com/hsimg/gds/550/807/807838_A1.jpg")
    private String imageUrl;

    @Schema(title = "goodsBadgeTypeCode", description = "상품뱃지타입코드", example = "RECOMMEND")
    private String goodsBadgeTypeCode;

    @Schema(title = "goodsBadgeTypeCodeName", description = "상품뱃지타입코드명", example = "추천")
    private String goodsBadgeTypeCodeName;

    @Schema(title = "vrYn", description = "vr 여부", example = "Y")
    private IsYn vrYn;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePathName);
    }
}
