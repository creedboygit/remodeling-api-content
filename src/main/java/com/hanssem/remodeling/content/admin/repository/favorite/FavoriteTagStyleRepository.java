package com.hanssem.remodeling.content.admin.repository.favorite;

import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteImageTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteTagStyleRepository  extends JpaRepository<FavoriteImageTag, Long> {

}
