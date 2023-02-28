package com.hanssem.remodeling.content.domain.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.domain.SystemEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_goods_sale_shop_type_code_d")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsSaleShopType extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALE_SHOP_TYPE_CODE_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODS_ID", nullable = false)
    private Goods goods;

    @Column(name = "SALE_SHOP_TYPE_CODE", nullable = false)
    private String saleShopTypeCode;

    public void associateGoods(Goods goods) {
        this.goods = goods;
    }
}
