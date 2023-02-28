package com.hanssem.remodeling.content.admin.controller.goods.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import com.hanssem.remodeling.content.constant.ImageType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(title = "GoodsImageResponse", description = "상품 이미지리스트 응답객체")
public class GoodsImageResponse implements Serializable {

    @Schema(title = "id", description = "상품이미지ID", example = "1")
    private Long id;

    @Schema(title = "imageSeq", description = "이미지순번", example = "1")
    private int imageSeq;

    @Schema(title = "imageType", description = "이미지타입", implementation = ImageType.class, example = "SQUARE")
    private ImageType imageType;

    @Schema(title = "imagePathName", description = "이미지패스명", example = "/gds/550/611/611262_B1.jpg")
    private String imagePathName;

    @Schema(title = "imageUrl", description = "이미지Url", example = "https://image.hanssem.com/hsimg/gds/550/611/611262_B1.jpg")
    private String imageUrl;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePathName);
    }
}

