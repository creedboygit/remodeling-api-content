package com.hanssem.remodeling.content.domain.favorite.entity;

import com.hanssem.remodeling.content.constant.FavoriteTagType;
import com.hanssem.remodeling.content.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Table(name = "tb_favorite_tag_m")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_TAG_ID")
    private Long id;

    @Column(name = "TAG_TYPE_CODE")
    @Enumerated(value = EnumType.STRING)
    private FavoriteTagType tagType;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "IMAGE_PATH_NAME")
    private String imagePath;

    @Column(name = "ETC_VALUE")
    private String etc;

}
