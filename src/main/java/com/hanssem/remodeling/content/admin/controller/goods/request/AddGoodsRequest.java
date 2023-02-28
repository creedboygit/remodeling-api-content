package com.hanssem.remodeling.content.admin.controller.goods.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.common.util.Utility;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AddGoodsRequest", description = "상품 등록 객체")
public class AddGoodsRequest {

    @NotBlank(message = "상품명을 입력해 주세요.")
    @Schema(title = "goodsName", description = "상품명", example = "모던베이지")
    private String goodsName;

    @Schema(title = "goodsGroupCode", description = "상품그룹코드", example = "O4O")
    private String goodsGroupCode;

    @Schema(title = "goodsStateCode", description = "상품상태코드", example = "N")
    private String goodsStateCode;

    @Schema(title = "saleBeginDatetime", description = "판매개시일시", example = "2022-07-26 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime saleBeginDatetime;

    @Schema(title = "saleEndDatetime", description = "판매종료일시", example = "2070-12-31 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime saleEndDatetime;

    @Schema(title = "displayYn", description = "디스플레이여부", example = "Y")
    private IsYn displayYn;

    @Schema(title = "standardCategoryNo", description = "규격카테고리NO", example = "16130")
    private int standardCategoryNo;

    @Schema(title = "consultRequestYn", description = "상담신청여부", example = "N")
    private IsYn consultRequestYn;

    @Schema(title = "eventYn", description = "이벤트여부", example = "N")
    private IsYn eventYn;

    @Schema(title = "vrYn", description = "VR여부", example = "N")
    private IsYn vrYn;

    @Schema(title = "goodsBadgeTypeCode", description = "상담뱃지유형코드", example = "BEST")
    private String goodsBadgeTypeCode;

    @Schema(title = "mallGoodsNo", description = "MALL상품NO", example = "855812")
    private Long mallGoodsNo;

    @Schema(title = "imagePathName", description = "정사각형대표이미지경로명", example = "/gds/550/817/817501_A1.jpg")
    private String imagePathName;

    @Schema(title = "descriptionContent", description = "설명내용", example = "")
    private String descriptionContent;

    @Schema(title = "recommendationContent", description = "추천내용", example = "")
    private String recommendationContent;

    @Schema(title = "webPageContent", description = "웹페이지내용", example = "")
    private String webPageContent;

    @Schema(title = "mobilePageContent", description = "모바일페이지내용", example = "")
    private String mobilePageContent;

    private List<AddGoodsCategoryRequest> categoryList = new ArrayList<>();

    private List<AddGoodsImageRequest> imageList = new ArrayList<>();

    private List<AddGoodsSaleShopTypeRequest> saleShopTypeList = new ArrayList<>();

    private List<AddGoodsKeywordSearchRequest> keywordSearchList = new ArrayList<>();

    private List<AddGoodsKeywordRecommendationTagRequest> keywordRecommendationTagList = new ArrayList<>();

    private List<AddGoodsKeywordStyleRequest> keywordStyleList = new ArrayList<>();

    public String getWebPageContent() {
        if (Objects.isNull(webPageContent)) return null;
        return Utility.replaceXSSEncoding(webPageContent);
    }

    public String getMobilePageContent() {
        if (Objects.isNull(mobilePageContent)) return null;
        return Utility.replaceXSSEncoding(mobilePageContent);
    }
}
