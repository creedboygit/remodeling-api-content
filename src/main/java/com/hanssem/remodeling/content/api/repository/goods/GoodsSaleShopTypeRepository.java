package com.hanssem.remodeling.content.api.repository.goods;

import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.hanssem.remodeling.content.domain.goods.entity.GoodsSaleShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsSaleShopTypeRepository extends JpaRepository<GoodsSaleShopType, Long> {
}
