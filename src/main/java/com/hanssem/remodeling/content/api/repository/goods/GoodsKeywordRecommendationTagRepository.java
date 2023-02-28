package com.hanssem.remodeling.content.api.repository.goods;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordRecommendationTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface GoodsKeywordRecommendationTagRepository extends JpaRepository<GoodsKeywordRecommendationTag, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsKeywordRecommendationTagRepository.findByGoodsId")})
    List<GoodsKeywordRecommendationTag> findByGoodsId(Long goodsId);

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsKeywordRecommendationTagRepository.findAllByGoodsId")})
    @Query("select t from GoodsKeywordRecommendationTag t where t.goods.id in :goodsIds")
    List<GoodsKeywordRecommendationTag> findAllByGoodsId(@Param("goodsIds") final List<Long> goodsIds);
}
