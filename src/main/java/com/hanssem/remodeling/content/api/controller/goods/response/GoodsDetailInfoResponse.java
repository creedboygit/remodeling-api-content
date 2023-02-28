package com.hanssem.remodeling.content.api.controller.goods.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import com.hanssem.remodeling.content.common.util.Utility;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "GoodsDetailInfoResponse", description = "상품 상세 전체 정보 응답객체")
public final class GoodsDetailInfoResponse implements Serializable {

    @Schema(title = "id", description = "상품ID", example = "966498")
    private Long id;

    @Schema(title = "scrapYn", description = "스크랩 여부", example = "Y")
    private String scrapYn;

    @Schema(title = "goodsName", description = "상품명", example = "모던베이지")
    private String goodsName;

    @Schema(title = "goodsGroupCode", description = "상품그룹코드", example = "O4O")
    private String goodsGroupCode;

    @Schema(title = "goodsStateCode", description = "상품상태코드", example = "N")
    private String goodsStateCode;

    @Schema(title = "saleBeginDatetime", description = "판매개시일시", example = "2022-07-26 00:00:00")
    private LocalDateTime saleBeginDatetime;

    @Schema(title = "saleEndDatetime", description = "판매종료일시", example = "2070-12-31 00:00:00")
    private LocalDateTime saleEndDatetime;

    @Schema(title = "displayYn", description = "디스플레이여부", example = "Y")
    private String displayYn;

    @Schema(title = "standardCategoryNo", description = "규격카테고리NO", example = "16130")
    private int standardCategoryNo;

    @Schema(title = "consultRequestYn", description = "상담신청여부", example = "N")
    private String consultRequestYn;

    @Schema(title = "eventYn", description = "이벤트여부", example = "N")
    private String eventYn;

    @Schema(title = "vrYn", description = "VR여부", example = "N")
    private String vrYn;

    @Schema(title = "goodsBadgeTypeCode", description = "상담뱃지유형코드", example = "BEST")
    private String goodsBadgeTypeCode;

    @Schema(title = "mallGoodsNo", description = "MALL상품NO", example = "855812")
    private Long mallGoodsNo;

    @Schema(title = "imagePathName", description = "정사각형대표이미지경로명", example = "/gds/550/817/817501_A1.jpg")
    private String imagePathName;

    @Schema(title = "imageUrl", description = "정사각형대표이미지 Url", example = "https://image.hanssem.com/hsimg/gds/550/807/807838_A1.jpg")
    private String imageUrl;

    @Schema(title = "descriptionContent", description = "설명내용", example = "")
    private String descriptionContent;

    @Schema(title = "recommendationContent", description = "추천내용", example = "")
    private String recommendationContent;

    @JsonIgnore
    @Schema(title = "recommendationTagName", description = "추천태그명", example = "")
    private String recommendationTagName;

    @Schema(title = "webPageContent", description = "웹페이지내용", example = "")
    private String webPageContent;

    @Schema(title = "mobilePageContent", description = "모바일페이지내용", example = "")
    private String mobilePageContent;

    @Builder.Default
    private List<CategoryResponse> categoryList = new ArrayList<>();

    @Builder.Default
    private List<GoodsImageResponse> imageList = new ArrayList<>();

    @Builder.Default
    private List<GoodsSaleShopTypeResponse> saleShopTypeList = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    private List<GoodsKeywordSearchResponse> keywordSearchList = new ArrayList<>();

    @Builder.Default
    private List<GoodsKeywordRecommendationTagResponse> recommendationTagList = new ArrayList<>();

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePathName);
    }

    public String getWebPageContent() {
        if (Objects.nonNull(webPageContent)) {
            return Utility.replaceXSSDecoding(webPageContent);
        } else {
            return Strings.EMPTY;
        }
    }

    public String getMobilePageContent() {
        if (Objects.nonNull(mobilePageContent)) {
            return Utility.replaceXSSDecoding(mobilePageContent);
        } else {
            return Strings.EMPTY;
        }
    }
}
