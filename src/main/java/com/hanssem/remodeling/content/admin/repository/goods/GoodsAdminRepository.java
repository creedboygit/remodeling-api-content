package com.hanssem.remodeling.content.admin.repository.goods;

import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsAdminRepository  extends JpaRepository<Goods, Long> {

}
