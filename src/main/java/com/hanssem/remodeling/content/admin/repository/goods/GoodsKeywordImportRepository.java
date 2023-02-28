package com.hanssem.remodeling.content.admin.repository.goods;

import com.hanssem.remodeling.content.domain.goods.entity.GoodsKeywordImport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoodsKeywordImportRepository extends JpaRepository<GoodsKeywordImport, Long> {

    @Query("select k from GoodsKeywordImport k where k.goodsId = :goodsId and k.keywordType = :type")
    List<GoodsKeywordImport> findByGoodsIdAndType(@Param("goodsId") Long goodsId, @Param("type") String type);

    @Query("select k from GoodsKeywordImport k where k.goodsId = :goodsId and k.keywordType = :type and k.keyword = :keyword")
    List<GoodsKeywordImport> findByIdAndName(@Param("goodsId") Long goodsId, @Param("type") String type, @Param("keyword") String keyword);

    @Query("select k from GoodsKeywordImport k where k.goodsId = :goodsId and k.keywordType = :type")
    List<GoodsKeywordImport> findByIdAndCodeName(@Param("goodsId") Long goodsId, @Param("type") String type);

}