package com.hanssem.remodeling.content.domain.goods.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("RELATED_STYLE")
public class GoodsKeywordRelatedStyle extends GoodsKeyword {

    @Column(name = "KEYWORD_SEQ", nullable = false)
    private int keywordSeq;

    @Column(name = "KEYWORD_NAME", nullable = false)
    private String keywordName;

}
