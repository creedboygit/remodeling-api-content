package com.hanssem.remodeling.content.domain.favorite.entity;

import com.hanssem.remodeling.content.domain.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_favorite_style_m")
public class FavoriteStyle extends BaseEntity {

    @Id
    @Column(name = "FAVORITE_STYLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MANAGE_NAME")
    private String manageName;

    @Column(name = "STYLE_NAME")
    private String styleName;

    @Column(name = "STYLE_CODE")
    private String styleCode;

    @Column(name = "DESCRIPTION_CONTENT")
    private String description;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "favoriteStyle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FavoriteStyleImage> styleImages = new ArrayList<>();

    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "favoriteStyle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FavoriteStyleTag> styleTags = new ArrayList<>();

    //==연관관계 메서드==//
    public void association() {
        for (FavoriteStyleImage image : getStyleImages()) {
            image.setFavoriteStyle(this);
        }
        for (FavoriteStyleTag tag : getStyleTags()) {
            tag.setFavoriteStyle(this);
        }
    }

}
