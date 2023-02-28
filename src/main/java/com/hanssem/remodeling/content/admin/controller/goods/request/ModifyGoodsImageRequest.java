package com.hanssem.remodeling.content.admin.controller.goods.request;

import com.hanssem.remodeling.content.constant.ImageType;
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
@Schema(title = "ModifyGoodsImageRequest", description = "상품 이미지리스트 수정 객체")
public class ModifyGoodsImageRequest implements Serializable {

    @Schema(title = "id", description = "상품이미지ID", example = "1")
    private Long id;

    @Schema(title = "imageSeq", description = "이미지순번", example = "1")
    private int imageSeq;

    @Schema(title = "imageType", description = "이미지타입", implementation = ImageType.class, example = "/gds/550/611/611262_B1.jpg")
    private ImageType imageType;

    @Schema(title = "imagePathName", description = "이미지패스명", example = "/gds/550/611/611262_B1.jpg")
    private String imagePathName;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ModifyGoodsImageRequest) {
            ModifyGoodsImageRequest request = (ModifyGoodsImageRequest) obj;
            return request.imagePathName.equals(((ModifyGoodsImageRequest) obj).getImagePathName());
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return imagePathName.hashCode();
    }
}
