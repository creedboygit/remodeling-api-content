package com.hanssem.remodeling.content.api.controller.goods.response;

import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseAllResponse;
import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseResponse;
import com.hanssem.remodeling.content.common.response.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(title = "GoodsDetailResponse", description = "상품 상세정보 응답객체")
public class GoodsDetailResponse implements Serializable {

    private GoodsDetailInfoResponse goods;
}
