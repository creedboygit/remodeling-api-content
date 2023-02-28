package com.hanssem.remodeling.content.domain.goods.entity;

import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsRequest;
import com.hanssem.remodeling.content.common.model.IsYn;
import com.hanssem.remodeling.content.domain.SystemEntity;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_goods_m")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Goods extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GOODS_ID")
    private Long id;

    @Column(name = "GOODS_NAME")
    private String goodsName;

    @Column(name = "GOODS_GROUP_CODE")
    private String goodsGroupCode;

    @Column(name = "GOODS_STATE_CODE")
    private String goodsStateCode;

    @Column(name = "SALE_BEGIN_DATETIME")
    private LocalDateTime saleBeginDatetime;

    @Column(name = "SALE_END_DATETIME")
    private LocalDateTime saleEndDatetime;

    @Column(name = "DISPLAY_YN")
    @Enumerated(value = EnumType.STRING)
    private IsYn displayYn;

    @Column(name = "STANDARD_CATEGORY_NO")
    private int standardCategoryNo;

    @Column(name = "CONSULT_REQUEST_YN")
    @Enumerated(value = EnumType.STRING)
    private IsYn consultRequestYn;

    @Column(name = "EVENT_YN")
    @Enumerated(value = EnumType.STRING)
    private IsYn eventYn;

    @Column(name = "VR_YN")
    @Enumerated(value = EnumType.STRING)
    private IsYn vrYn;

    @Column(name = "GOODS_BADGE_TYPE_CODE")
    private String goodsBadgeTypeCode;

    @Column(name = "MALL_GOODS_NO")
    private Long mallGoodsNo;

    @Column(name = "IMAGE_PATH_NAME")
    private String imagePathName;

    @Column(name = "DESCRIPTION_CONTENT")
    private String descriptionContent;

    @Column(name = "RECOMMENDATION_CONTENT")
    private String recommendationContent;

    @Column(name = "WEB_PAGE_CONTENT")
    private String webPageContent;

    @Column(name = "MOBILE_PAGE_CONTENT")
    private String mobilePageContent;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("categoryDisplaySeq asc")
    @Builder.Default
    private List<GoodsCategory> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GoodsImage> imageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsSaleShopType> saleShopTypeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("keywordSeq asc")
    private List<GoodsKeywordSearch> keywordSearchList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("keywordSeq asc")
    private List<GoodsKeywordRecommendationTag> keywordRecommendationTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("keywordSeq asc")
    private List<GoodsKeywordStyle> keywordStyleList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("keywordSeq asc")
    private List<GoodsKeywordRelatedStyle> keywordRelatedStyleList = new ArrayList<>();

    public void associateGoods() {
        for (GoodsCategory goodsCategory : categoryList) {
            goodsCategory.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsCategory.associateGoods(this);
        }

        for (GoodsImage goodsImage : imageList) {
            goodsImage.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsImage.associateGoods(this);
        }

        for (GoodsSaleShopType goodsSaleShopType : saleShopTypeList) {
            goodsSaleShopType.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsSaleShopType.associateGoods(this);
        }

        for (GoodsKeywordSearch goodsKeywordSearch : keywordSearchList) {
            goodsKeywordSearch.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsKeywordSearch.associateGoods(this);
        }

        for (GoodsKeywordRecommendationTag goodsKeywordRecommendationTag : keywordRecommendationTagList) {
            goodsKeywordRecommendationTag.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsKeywordRecommendationTag.associateGoods(this);
        }

        for (GoodsKeywordStyle goodsKeywordStyle : keywordStyleList) {
            goodsKeywordStyle.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsKeywordStyle.associateGoods(this);
        }

        for (GoodsKeywordRelatedStyle goodsKeywordRelatedStyle : keywordRelatedStyleList) {
            goodsKeywordRelatedStyle.setSystemUpdaterId(this.getSystemUpdaterId());
            goodsKeywordRelatedStyle.associateGoods(this);
        }
    }

    public void modifyStateCode(String code) {
        this.goodsStateCode = code;
    }

    public void modifyBadgeCode(String code) {
        this.goodsBadgeTypeCode = code;
    }

    public void modifyVrYn(IsYn isYn) {
        this.vrYn = isYn;
    }

    public void modifyEventYn(IsYn isYn) {
        this.eventYn = isYn;
    }

    public void modifyGoods(ModifyGoodsRequest request) {
        goodsName = request.getGoodsName();
        goodsGroupCode = request.getGoodsGroupCode();
        goodsStateCode = request.getGoodsStateCode();
        saleBeginDatetime = request.getSaleBeginDatetime();
        saleEndDatetime = request.getSaleEndDatetime();
        displayYn = request.getDisplayYn();
        standardCategoryNo = request.getStandardCategoryNo();
        consultRequestYn = request.getConsultRequestYn();
        eventYn = request.getEventYn();
        vrYn = request.getVrYn();
        goodsBadgeTypeCode = request.getGoodsBadgeTypeCode();
        mallGoodsNo = request.getMallGoodsNo();
        imagePathName = request.getImagePathName();
        descriptionContent = request.getDescriptionContent();
        recommendationContent = request.getRecommendationContent();
        webPageContent = request.getWebPageContent();
        mobilePageContent = request.getMobilePageContent();
    }

    public void modifyCategory(List<GoodsCategory> cate) {
        categoryList.clear();
        categoryList.addAll(cate);
    }

    public void modifyImage(List<GoodsImage> images) {
        imageList.clear();
        imageList.addAll(images);
    }

    public void modifyGoodsSaleShopType(List<GoodsSaleShopType> goodsSaleShopType) {
        saleShopTypeList.clear();
        saleShopTypeList.addAll(goodsSaleShopType);
    }

    public void modifyGoodsKeywordSearch(List<GoodsKeywordSearch> keywordSearches) {
        keywordSearchList.clear();
        keywordSearchList.addAll(keywordSearches);
    }

    public void modifyGoodsKeywordRecommendationTag(List<GoodsKeywordRecommendationTag> recommendTags) {
        keywordRecommendationTagList.clear();
        keywordRecommendationTagList.addAll(recommendTags);
    }

    public void modifyGoodsKeywordStyle(List<GoodsKeywordStyle> styles) {
        keywordStyleList.clear();
        keywordStyleList.addAll(styles);
    }

    /**
     * 데이터 기본값 세팅
     */
    public void setDefaultValues() {
        if (Objects.isNull(displayYn)) {
            displayYn = IsYn.N;
        }
        if (Objects.isNull(consultRequestYn)) {
            consultRequestYn = IsYn.N;
        }
        if (Objects.isNull(eventYn)) {
            eventYn = IsYn.N;
        }
        if (Objects.isNull(vrYn)) {
            vrYn = IsYn.N;
        }
        if (Objects.isNull(saleBeginDatetime)) {
            saleBeginDatetime = LocalDateTime.now();
        }
        if (Objects.isNull(saleEndDatetime)) {
            saleEndDatetime = LocalDateTime.of(2999, Month.DECEMBER, 31, 0, 0, 0);
        }
    }
}
