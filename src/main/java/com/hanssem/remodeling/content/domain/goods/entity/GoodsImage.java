package com.hanssem.remodeling.content.domain.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.constant.ImageType;
import com.hanssem.remodeling.content.domain.SystemEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_goods_image_d")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsImage extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GOODS_IMAGE_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GOODS_ID")
    private Goods goods;

    @Column(name = "IMAGE_SEQ")
    private int imageSeq;

    @Column(name = "IMAGE_TYPE")
    @Enumerated(value = EnumType.STRING)
    private ImageType imageType;

    @Column(name = "IMAGE_PATH_NAME")
    private String imagePathName;

    public void associateGoods(Goods goods) {
        this.goods = goods;
    }
}
