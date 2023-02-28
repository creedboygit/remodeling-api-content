package com.hanssem.remodeling.content.admin.controller.goods.request;

import com.hanssem.remodeling.content.common.model.PageAndSortRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Order;

@Schema(title = "GoodsSimpleSearchRequest", description = "상품검색 파라메터 객체")
@Getter
@Setter
@ParameterObject
public class GoodsSimpleSearchRequest extends PageAndSortRequest {

    @Schema(name = "goodsStateCode", title = "상품상태코드", type = "String", description = "N(판매중) A(삭제) E(단종) P(판매보류) R(판매대기) SD(일시품절) SH(품절) X1(임시저장)", defaultValue = "N", hidden = true, example = "N")
    private String goodsStateCode;

    @Schema(name = "categoryLargeNo", title = "상품 카테고리(대)", type = "int", required = false, example = "16110")
    private int categoryLargeNo;

    @Schema(name = "categoryMiddleNo", title = "상품 카테고리(중)", type = "int", required = false, example = "16113")
    private int categoryMiddleNo;

    @Schema(name = "categorySmallNo", title = "상품 카테고리(소)", type = "int", required = false, example = "16125")
    private int categorySmallNo;

    @Schema(name = "categoryTinyNo", title = "상품 카테고리(세)", type = "int", required = false, example = "16137")
    private int categoryTinyNo;


    @Schema(name = "goodsBadgeTypeCode", title = "상품뱃지타입코드", type = "String", required = false, example = "BEST")
    private String goodsBadgeTypeCode;

    @Schema(name = "goodsName", title = "상품명", type = "String", required = false, example = "")
    private String goodsName;

    @Schema(name = "goodsId", title = "상품ID", type = "Long",  required = false, example = "")
    private Long goodsId;

    protected GoodsSimpleSearchRequest(int page, int size, List<Order> sorts) {
        super(page, size, sorts);
    }
}
