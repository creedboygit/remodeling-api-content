package com.hanssem.remodeling.content.admin.controller.goods.request;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.constant.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "ModifyGoodsIsYnRequest", description = "상품 수정 객체")
@Getter
@Setter
@ToString
@ParameterObject
public class ModifyGoodsIsYnRequest {

    @NotNull(message = "code 은(는) 필수 입니다.")
    @Schema(title = "isYn", description = "isYn", type = "string", example = "Y", implementation = IsYn.class)
    private IsYn isYn;

    @NotNull(message = "상품 ID 은(는) 필수 입니다.")
    @Schema(title = "goodsIdList", description = "상품 ID", example = "1")
    private List<Long> goodsIdList;

    public void valid() {
        if (this.isYn == null) {
            throw new CustomException(ResponseCode.GOODS_INVALID_CODE);
        }
        if (this.goodsIdList == null || this.goodsIdList.isEmpty()) {
            throw new CustomException(ResponseCode.GOODS_ID_IS_EMPTY);
        }
    }
}
