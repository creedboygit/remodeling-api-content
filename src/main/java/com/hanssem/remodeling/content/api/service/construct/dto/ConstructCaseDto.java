package com.hanssem.remodeling.content.api.service.construct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springdoc.api.annotations.ParameterObject;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Builder
public class ConstructCaseDto {

    @Schema(name = "sortType", title = "조회정렬방식", description = "R(최신순 default), F(역대인기순), N(최근인기순)", type = "String", example = "R")
//    private String sort;
    private String sortType;

    @Schema(name = "curPage", title = "페이지번호", type = "String", example = "1")
//    private String page;
    private String curPage;

    @Schema(name = "pageRow", title = "페이징개수", type = "String", example = "20")
//    private String size;
    private String pageRow;

    @Schema(name = "custNo", title = "회원번호", type = "String", example = "")
    private String custNo;

    @Schema(name = "shopId", title = "매장번호(이 매장의 시공사례)", type = "String", example = "")
    private String shopId;

    @Schema(name = "goodsId", title = "상품번호(이 상품의 시공사례)", type = "String", example = "613660")
    private String goodsId;

    @Schema(name = "exhibitHall", title = "매장 전문관 코드(RM:리모델링, KCBR:키친&바스, FN:가구, HG:생용)", type = "String", example = "6136RM60")
    private String exhibitHall;

    @Schema(name = "typeValue", title = "ScreenType(M:모바일, P:PC) - 기본값 : M", type = "String", example = "M")
    private String typeValue;
}