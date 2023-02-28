package com.hanssem.remodeling.content.api.repository.goods;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordSearch;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsKeywordSearchRepository extends JpaRepository<GoodsKeywordSearch, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsKeywordSearchRepository.findByGoodsId")})
    List<GoodsKeywordSearch> findByGoodsId(Long goodsId);
}
