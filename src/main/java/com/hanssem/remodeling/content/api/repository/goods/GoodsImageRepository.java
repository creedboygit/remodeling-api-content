package com.hanssem.remodeling.content.api.repository.goods;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategory;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsImageRepository.findByGoodsIdOrderByImageSeq")})
    List<GoodsImage> findByGoodsIdOrderByImageSeq(Long goodsId);
}
