package com.hanssem.remodeling.content.admin.controller.goods.request;

import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.common.model.PageAndSortRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Sort.Order;

@Schema(title = "GoodsSearchRequest", description = "상품검색 파라메터 객체")
@Getter
@Setter
@ParameterObject
public class GoodsSearchRequest extends PageAndSortRequest {

    @Schema(name = "categoryLargeNo", title = "상품 카테고리(대)", type = "int", example = "16266")
    private int categoryLargeNo;

    @Schema(name = "categoryMiddleNo", title = "상품 카테고리(중)", type = "int", example = "16286")
    private int categoryMiddleNo;

    @Schema(name = "categorySmallNo", title = "상품 카테고리(소)", type = "int", example = "16301")
    private int categorySmallNo;

    @Schema(name = "categoryTinyNo", title = "상품 카테고리(세)", type = "int", example = "16302")
    private int categoryTinyNo;

    @Schema(name = "goodsName", title = "상품명", type = "String", example = "")
    private String goodsName;

    @Schema(name = "goodsStateCode", title = "상품상태코드", type = "String", example = "N")
    private String goodsStateCode;

    @Schema(name = "goodsBadgeTypeCode", title = "상품뱃지타입코드", type = "String", example = "BEST")
    private String goodsBadgeTypeCode;

    @Schema(name = "displayYn", title = "노출여부", type = "com.hanssem.remodeling.content.common.model.IsYn", example = "Y")
    private IsYn displayYn;

    @Schema(name = "vrYn", title = "vr여부", type = "com.hanssem.remodeling.content.common.model.IsYn", example = "Y")
    private IsYn vrYn;

    @Schema(name = "eventYn", title = "이벤트여부", type = "com.hanssem.remodeling.content.common.model.IsYn", example = "Y")
    private IsYn eventYn;

    @Schema(name = "consultRequestYn", title = "상담신청여부", type = "com.hanssem.remodeling.content.common.model.IsYn", example = "Y")
    private IsYn consultRequestYn;

    @Schema(name = "styleCode", title = "스타일코드", type = "String", example = "modern")
    private String styleCode;

    @Schema(name = "goodsIds", title = "상품ID 목록", type = "List<Long>", example = "")
    private List<Long> goodsIds;

    protected GoodsSearchRequest(int page, int size, List<Order> sorts) {
        super(page, size, sorts);
    }
}
