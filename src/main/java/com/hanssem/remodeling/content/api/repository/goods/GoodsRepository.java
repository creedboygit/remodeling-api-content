package com.hanssem.remodeling.content.api.repository.goods;

import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

import static org.hibernate.annotations.QueryHints.COMMENT;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsRepository.findByStyleCode")})
    @Query("select g "
        + "from Goods g "
        + "join GoodsKeywordStyle s on g.id = s.goods.id "
        + "where s.keywordName = :styleCode")
    List<Goods> findByStyleCode(@Param("styleCode") String styleCode);
}
