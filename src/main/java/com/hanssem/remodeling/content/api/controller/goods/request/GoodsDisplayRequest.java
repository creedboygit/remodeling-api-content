package com.hanssem.remodeling.content.api.controller.goods.request;

import com.hanssem.remodeling.content.common.model.PageAndSortRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(title = "GoodsDisplayRequest", description = "상품 리스트 요청 객체 (전시용)")
@Getter
@Setter
@ParameterObject
public class GoodsDisplayRequest extends PageAndSortRequest {

    @Schema(name = "goodsStateCode", title = "상품상태코드", type = "String", description = "X1:임시저장, N:판매중, SH:품절, A:삭제", defaultValue = "N", allowableValues = {"X1", "N", "SH", "A"}, example = "N")
    private String goodsStateCode;

    @Schema(name = "categoryNo", title = "상품 카테고리", type = "int", example = "16266")
    private int categoryNo;

    @Schema(name = "goodsBadgeTypeCode", title = "상품뱃지타입코드", type = "String", description = "BEST, NEW, RECOMMEND", allowableValues = {"BEST", "NEW", "RECOMMEND"}, example = "BEST")
    private String goodsBadgeTypeCode;

    protected GoodsDisplayRequest(int page, int size, List<Sort.Order> sorts) {
        super(page, size, sorts);
    }
}
