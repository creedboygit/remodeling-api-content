package com.hanssem.remodeling.content.domain.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.domain.SystemEntity;
import lombok.*;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

@Entity
@Table(name = "tb_goods_keyword_d")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorOptions(force=true)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.STRING,
        name = "KEYWORD_TYPE_CODE",
        columnDefinition = "varchar(50)"
)
public class GoodsKeyword extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KEYWORD_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODS_ID", nullable = false)
    private Goods goods;

    public void associateGoods(Goods goods) {
        this.goods = goods;
    }
}
