package com.hanssem.remodeling.content.api.controller.goods.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(title = "GoodsDetailInfoSimpleResponse", description = "상품 상세 정보 응답객체 (상담신청용)")
public final class GoodsDetailInfoSimpleResponse implements Serializable {

    @Schema(title = "id", description = "상품ID", example = "966498")
    private Long id;

    @Schema(title = "goodsName", description = "상품명", example = "모던베이지")
    private String goodsName;

    @Schema(title = "imagePathName", description = "정사각형대표이미지경로명", example = "/gds/550/817/817501_A1.jpg")
    private String imagePathName;

    @Schema(title = "imageUrl", description = "이미지경로 Url", example = "https://image.hanssem.com/hsimg/gds/550/807/807838_A1.jpg")
    private String imageUrl;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePathName);
    }

}
