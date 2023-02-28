package com.hanssem.remodeling.content.domain.favorite.entity;

import com.hanssem.remodeling.content.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "tb_favorite_image_tag_d")
public class FavoriteImageTag extends BaseEntity {

    @Id
    @Column(name = "FAVORITE_IMAGE_TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FAVORITE_TAG_ID")
    private Long favoriteTagId;

    @Column(name = "FAVORITE_IMAGE_ID")
    private Long favoriteImageId;

}