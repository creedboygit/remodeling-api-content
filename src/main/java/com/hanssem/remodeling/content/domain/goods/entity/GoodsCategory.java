package com.hanssem.remodeling.content.domain.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.domain.SystemEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_goods_category_d")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsCategory extends SystemEntity {

    @Id
    @Column(name = "GOODS_CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GOODS_ID", nullable = false)
    private Goods goods;

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

    public void associateGoods(final Goods goods) {
        this.goods = goods;
    }
}
