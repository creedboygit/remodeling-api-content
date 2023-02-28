package com.hanssem.remodeling.content.api.controller.goods.request;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.PageAndSortRequest;
import com.hanssem.remodeling.content.constant.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Schema(title = "GoodsDisplayMashUpRequest", description = "상품 리스트 요청 객체 (전시용 MashUp)")
@Getter
@Setter
@Slf4j
@ParameterObject
@ToString
public class GoodsDisplayMashUpRequest extends PageAndSortRequest {

    @Schema(name = "goodsStateCode", title = "상품상태코드", type = "String", description = "X1:임시저장, N:판매중, SH:품절, A:삭제", defaultValue = "N", allowableValues = {"X1", "N", "SH", "A"}, example = "N")
    private String goodsStateCode;

    @Schema(name = "categoryNo", title = "상품 카테고리", type = "Integer", example = "16266")
    private int categoryNo;

    protected GoodsDisplayMashUpRequest(int page, int size, List<Sort.Order> sorts) {
        super(page, size, sorts);
    }
}
