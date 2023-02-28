package com.hanssem.remodeling.content.api.controller.construct.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.NotBlank;

@Schema(name = "ConstructCaseRequest", description = "시공사례 조회 요청 객체 (한샘몰 API)")
@Getter
@Setter
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ConstructCaseRequest {

    @NotBlank(message = "조회정렬방식을 입력해주세요.")
    @Schema(name = "sort", title = "조회정렬방식", description = "R:최신순(default), F:역대인기순, N:최근인기순", defaultValue = "R", example = "R", allowableValues = {"R", "F", "N"})
    private String sort;

    @NotBlank(message = "페이지번호를 입력해주세요.")
    @Schema(name = "page", title = "페이지번호", example = "1")
    private String page;

    @NotBlank(message = "페이징개수를 입력해주세요.")
    @Schema(name = "size", title = "페이징개수", example = "20")
    private String size;

    @Schema(name = "custNo", title = "회원번호", type = "String", example = "")
    private String custNo;

    @Schema(name = "shopId", title = "매장번호(이 매장의 시공사례)", type = "String", example = "")
    private String shopId;

    @Schema(name = "goodsId", title = "상품번호(이 상품의 시공사례)", type = "String", example = "613660")
    private String goodsId;

    @Schema(name = "exhibitHall", title = "매장 전문관 코드(RM:리모델링, KCBR:키친&바스, FN:가구, HG:생용)", type = "String", example = "KCBR", allowableValues = {"RM", "FN", "HG"})
    private String exhibitHall;

    @Schema(name = "typeValue", title = "ScreenType(M:모바일, P:PC) - 기본값 : M", type = "String", defaultValue = "M", example = "M", allowableValues = {"M", "P"})
    private String typeValue;
}
