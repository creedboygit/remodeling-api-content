package com.hanssem.remodeling.content.admin.controller.goods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "GoodsListResponse", description = "상품검색 목록조회 객체")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsListResponse {

    @Schema(title = "id", description = "상품 ID", example = "807838")
    private Long id;

    @Schema(title = "goodsName", description = "상품명", example = "(40평대) 모던 크리스탈 화이트 패키지")
    private String goodsName;

    @JsonIgnore
    @Schema(title = "imagePathName", description = "상품 대표 이미지", hidden = true, example = "/gds/550/807/807838_A1.jpg")
    private String imagePathName;

    @Schema(title = "imageUrl", description = "상품 정사각형 대표 이미지 Url", example = "https://image.hanssem.com/hsimg/gds/550/807/807838_A1.jpg")
    private String imageUrl;

    @Schema(title = "goodsStateCode", description = "상품상태코드", example = "N")
    private String goodsStateCode;

    @Schema(title = "goodsStateCodeName", description = "상품상태코드명", example = "판매중")
    private String goodsStateCodeName;

    @Schema(title = "goodsBadgeTypeCode", description = "상품뱃지타입코드", example = "RECOMMEND")
    private String goodsBadgeTypeCode;

    @Schema(title = "goodsBadgeTypeCodeName", description = "상담뱃지유형코드명", example = "추천")
    private String goodsBadgeTypeCodeName;

    @Schema(title = "standardCategoryNo", description = "대표 카테고리번호", example = "16138")
    private int standardCategoryNo;

    @Schema(title = "mallGoodsNo", description = "MALL상품NO", example = "855812")
    private Long mallGoodsNo;

    @Schema(title = "eventYn", description = "이벤트여부", example = "Y")
    private IsYn eventYn;

    @Schema(title = "consultRequestYn", description = "상담신청여부", example = "Y")
    private IsYn consultRequestYn;

    @Schema(title = "displayYn", description = "노출여부", example = "Y")
    private IsYn displayYn;

    @Schema(title = "vrYn", description = "vr여부", example = "Y")
    private IsYn vrYn;

    @Schema(title = "standardCategoryName", description = "대표 카테고리 한글명", example = "리모델링 패키지>트렌드 제안>40평추천>40평추천")
    private String standardCategoryName;

    @Schema(title = "styleCode", description = "스타일코드", example = "modern")
    private String styleCode;

    @Schema(title = "systemCreateDateTime", description = "등록일시", example = "2023-02-23 10:00:00")
    private LocalDateTime systemCreateDateTime;

    @Schema(title = "systemConstructorId", description = "등록자ID", example = "12345678")
    private String systemConstructorId;

    @Schema(title = "systemUpdateDatetime", description = "수정일시", example = "2023-02-23 10:00:00")
    private LocalDateTime systemUpdateDatetime;

    @Schema(title = "systemUpdaterId", description = "수정자ID", example = "12345678")
    private String systemUpdaterId;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePathName);
    }
}
