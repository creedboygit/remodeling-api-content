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
@Schema(title = "ModifyGoodsSaleShopTypeRequest", description = "상품 판매매장유형 리스트 수정 객체")
public class ModifyGoodsSaleShopTypeRequest implements Serializable {

    @Schema(title = "id", description = "ID", example = "1")
    private Long id;

    @Schema(title = "mobilePageContent", description = "판매매장타입코드ID", example = "S01")
    private String saleShopTypeCode;
}
