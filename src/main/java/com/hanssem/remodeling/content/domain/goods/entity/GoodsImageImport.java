package com.hanssem.remodeling.content.domain.goods.entity;

import com.hanssem.remodeling.content.constant.ImageType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_goods_image_d")
public class GoodsImageImport {

    @Id
    @Column(name = "GOODS_IMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOODS_ID")
    private Long goodsId;

    @Column(name = "IMAGE_SEQ")
    private int imageSeq;

    @Column(name = "IMAGE_TYPE")
    @Enumerated(value = EnumType.STRING)
    private ImageType imageType;

    @Column(name = "IMAGE_PATH_NAME")
    private String imagePathName;

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
}
