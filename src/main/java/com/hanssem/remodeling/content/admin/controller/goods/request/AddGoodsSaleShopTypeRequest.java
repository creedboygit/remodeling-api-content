package com.hanssem.remodeling.content.admin.controller.goods.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "AddGoodsSaleShopTypeRequest", description = "상품 판매매장유형 리스트 등록 객체")
public class AddGoodsSaleShopTypeRequest implements Serializable {

    @Schema(title = "mobilePageContent", description = "판매매장타입코드ID", example = "S01")
    private String saleShopTypeCode;
}
