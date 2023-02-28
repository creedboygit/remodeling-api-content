package com.hanssem.remodeling.content.api.controller.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(title = "GoodsSaleShopTypeResponse", description = "상품 판매매장유형 리스트 응답객체")
public class GoodsSaleShopTypeResponse implements Serializable {

    @Schema(title = "mobilePageContent", description = "판매매장타입코드ID", example = "S01")
    private String saleShopTypeCode;
}
