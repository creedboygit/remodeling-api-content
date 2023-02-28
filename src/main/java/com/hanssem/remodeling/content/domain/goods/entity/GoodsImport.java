package com.hanssem.remodeling.content.domain.goods.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_goods_m")
public class GoodsImport implements Serializable {

    private static final long serialVersionUID = 1547271639930071088L;

    @Id
    @Column(name = "GOODS_ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOODS_NAME")
    private String goodsName;

    @Column(name = "GOODS_GROUP_CODE")
    private String goodsGroupCode;

    @Column(name = "GOODS_STATE_CODE")
    private String goodsStateCode;

    @Column(name = "SALE_BEGIN_DATETIME")
    private LocalDateTime saleBeginDateTime;

    @Column(name = "SALE_END_DATETIME")
    private LocalDateTime saleEndDateTime;

    @Column(name = "DISPLAY_YN")
    private String displayYn;

    @Column(name = "STANDARD_CATEGORY_NO")
    private int standardCategoryNo;

    @Column(name = "CONSULT_REQUEST_YN")
    private String consultRequestYn;

    @Column(name = "EVENT_YN")
    private String eventYn;

    @Column(name = "VR_YN")
    private String vrYn; //10

    @Column(name = "GOODS_BADGE_TYPE_CODE")
    private String goodsBadgeTypeCode;

    @Column(name = "MALL_GOODS_NO")
    private String mallGoodsNo;

    @Column(name = "IMAGE_PATH_NAME")
    private String imagePath;

    @Column(name = "DESCRIPTION_CONTENT")
    private String description;

    @Column(name = "RECOMMENDATION_CONTENT")
    private String recommendContent;

    @Column(name = "WEB_PAGE_CONTENT", columnDefinition="TEXT")
    private String webPageContent;

    @Column(name = "MOBILE_PAGE_CONTENT", columnDefinition="TEXT")
    private String mobilePageContent;

    @Column(name = "SYSTEM_CREATE_DATETIME")
//    @CreatedDate
    private LocalDateTime systemCreateDateTime;

    @Column(name = "SYSTEM_CONSTRUCTOR_ID")
    private String systemConstructorId;

    @Column(name= "SYSTEM_UPDATE_DATETIME")
//    @LastModifiedDate
    private LocalDateTime systemUpdateDateTime;

    @Column(name = "SYSTEM_UPDATER_ID")
    private String systemUpdaterId;

    public void setStandardCategoryNo(int standardCategoryNo) {
        this.standardCategoryNo = standardCategoryNo;
    }

    @Override
    public String toString() {
        return "GoodsImport{" +
            "id=" + id +
            ", goodsName='" + goodsName + '\'' +
            '}';
    }
}
