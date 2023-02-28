package com.hanssem.remodeling.content.admin.controller.goods.request;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

@Getter
@Setter
@ParameterObject
@Schema(title = "GoodsModifyGoodsStateRequest", description = "상품 판매상태 수정 객체")
public class ModifyGoodsCodeRequest {

    @Schema(name = "code", title = "코드", type = "String", description = "공통코드 api에서 코드 조회하여 목록 출력 해주세요.")
    private String code;

    @Schema(name = "goodsIdList", title = "상품 ID", type = "List<Long>", description = "상품 ID 목록")
    private List<Long> goodsIdList;

    public void valid() {
        if (Objects.isNull(goodsIdList) || this.goodsIdList.isEmpty()) {
            throw new CustomException(ResponseCode.GOODS_ID_IS_EMPTY);
        }
    }
}
