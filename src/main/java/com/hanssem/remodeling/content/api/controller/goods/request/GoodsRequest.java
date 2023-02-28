package com.hanssem.remodeling.content.api.controller.goods.request;

import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.common.model.PageAndSortRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Sort;

import java.util.List;

@Schema(title = "GoodsRequest", description = "상품 리스트 요청 객체")
@Getter
@Setter
@ParameterObject
public class GoodsRequest extends PageAndSortRequest {

//    @Hidden
    @Schema(name = "goodsStateCode", title = "상품상태코드", type = "String", description = "X1:임시저장, N:판매중, SH:품절, A:삭제", defaultValue = "N", allowableValues = {"X1", "N", "SH", "A"}, example = "N", hidden = true)
    private String goodsStateCode;

    @Schema(name = "displayYn", title = "노출여부", type = "com.hanssem.remodeling.content.common.model.IsYn",  defaultValue = "Y", example = "Y", hidden = true)
    private IsYn displayYn;

    @Schema(name = "categoryLargeNo", title = "상품 카테고리(대)", type = "int", example = "16266")
    private int categoryLargeNo;

    @Schema(name = "categoryMiddleNo", title = "상품 카테고리(중)", type = "int", example = "16268")
    private int categoryMiddleNo;

    @Schema(name = "categorySmallNo", title = "상품 카테고리(소)", type = "int", example = "16289")
    private int categorySmallNo;

    @Schema(name = "goodsBadgeTypeCode", title = "상품뱃지타입코드", type = "String", description = "BEST, NEW, RECOMMEND", allowableValues = {"BEST", "NEW", "RECOMMEND"}, example = "BEST")
    private String goodsBadgeTypeCode;

    public void initRequestParam() {
        if (goodsStateCode == null || goodsStateCode.isEmpty()) {
            setGoodsStateCode("N");
        }
        if (displayYn == null) {
            displayYn = IsYn.Y;
        }
    }

    public GoodsRequest(int page, int size, List<Sort.Order> sorts) {
        super(page, size, sorts);
    }
}
