package com.hanssem.remodeling.content.domain.goods.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "tb_goods_category_d_mig")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsCategoryMigration {

    @Id
    @Column(name = "GOODS_CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "GOODS_ID", nullable = false)
//    private Goods goods;

    @Column(name = "GOODS_ID", nullable = false)
    private Long goodsId;

    @Column(name = "CATEGORY_NO", nullable = false)
    private int categoryNo;

    @Column(name = "CATEGORY_LRCL_NO", nullable = false)
    private int categoryLargeNo;

    @Column(name = "CATEGORY_MDCL_NO", nullable = false)
    private int categoryMiddleNo;

    @Column(name = "CATEGORY_SMCL_NO", nullable = false)
    private int categorySmallNo;

    @Column(name = "CATEGORY_TNCL_NO", nullable = false)
    private int categoryTinyNo;

    @Column(name = "CATEGORY_LRCL_NAME", nullable = false)
    private String categoryLargeName;

    @Column(name = "CATEGORY_MDCL_NAME", nullable = false)
    private String categoryMiddleName;

    @Column(name = "CATEGORY_SMCL_NAME", nullable = false)
    private String categorySmallName;

    @Column(name = "CATEGORY_TNCL_NAME", nullable = false)
    private String categoryTinyName;

    @Column(name = "CATEGORY_DISPLAY_SEQ")
    private int categoryDisplaySeq;

    @Column(name = "SYSTEM_CREATE_DATETIME")
    @CreatedDate
    private LocalDateTime systemCreateDateTime;

    @Column(name = "SYSTEM_CONSTRUCTOR_ID")
    private String systemConstructorId;

    @Column(name= "SYSTEM_UPDATE_DATETIME")
    @LastModifiedDate
    private LocalDateTime systemUpdateDatetime;

    @Column(name = "SYSTEM_UPDATER_ID")
    private String systemUpdaterId;

//    public void associateGoods(final Goods goods) {
//        this.goods = goods;
//    }
}
