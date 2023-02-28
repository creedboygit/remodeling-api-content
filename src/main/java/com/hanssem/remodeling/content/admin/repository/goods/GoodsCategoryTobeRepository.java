package com.hanssem.remodeling.content.admin.repository.goods;

import com.hanssem.remodeling.content.domain.goods.entity.GoodsCategoryTobe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoodsCategoryTobeRepository  extends JpaRepository<GoodsCategoryTobe, Long> {

    @Query("select c from GoodsCategoryTobe c where c.ctgNm_S = :ctgNm")
    List<GoodsCategoryTobe> findByCtgNm(@Param("ctgNm") String categoryTinyName);

}
