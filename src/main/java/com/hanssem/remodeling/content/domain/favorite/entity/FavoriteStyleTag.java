package com.hanssem.remodeling.content.domain.favorite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_favorite_style_tag_d")
public class FavoriteStyleTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_STYLE_TAG_ID")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FAVORITE_STYLE_ID")
    private FavoriteStyle favoriteStyle;

    @Column(name = "FAVORITE_TAG_ID")
    private Long favoriteTagId;

    public void setFavoriteStyle(FavoriteStyle favoriteStyle) {
        this.favoriteStyle = favoriteStyle;
    }
}