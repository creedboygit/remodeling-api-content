package com.hanssem.remodeling.content.api.controller.goods.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "GoodsListResponse", description = "상품 리스트 조회 응답 객체")
public class GoodsListResponse implements Serializable {

    @Schema(title = "id", description = "상품 ID", example = "611262")
    private Long id;

    @Schema(title = "goodsName", description = "상품명", example = "EO300 터치 화이트 & 블랙")
    private String goodsName;

    @Schema(title = "imagePathName", description = "정사각형대표이미지경로명", example = "/gds/550/807/807838_A1.jpg")
    private String imagePathName;

    @Schema(title = "imageUrl", description = "정사각형대표이미지 Url", example = "https://image.hanssem.com/hsimg/gds/550/807/807838_A1.jpg")
    private String imageUrl;

    @Schema(title = "goodsStateCode", description = "상품상태코드", example = "N")
    private String goodsStateCode;

    @Schema(title = "goodsStateCodeName", description = "상품상태코드명", example = "판매중")
    private String goodsStateCodeName;

    @Schema(title = "goodsBadgeTypeCode", description = "상품뱃지타입코드", example = "RECOMMEND")
    private String goodsBadgeTypeCode;

    @Schema(title = "goodsBadgeTypeCodeName", description = "상품뱃지타입코드명", example = "추천")
    private String goodsBadgeTypeCodeName;

    @Schema(title = "recommendationTagList", description = "상품 추천 태그", type = "List<String>", example = "추천태그명")
    private List<GoodsKeywordRecommendationTagResponse> recommendationTagList = new ArrayList<>();

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePathName);
    }
}
