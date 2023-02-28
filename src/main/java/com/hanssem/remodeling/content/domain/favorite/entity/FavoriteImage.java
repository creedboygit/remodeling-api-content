package com.hanssem.remodeling.content.domain.favorite.entity;

import com.hanssem.remodeling.content.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "tb_favorite_image_m")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_IMAGE_ID")
    private Long id;

    @Column(name = "IMAGE_PATH_NAME")
    private String imagePath;

}