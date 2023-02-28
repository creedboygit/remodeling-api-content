package com.hanssem.remodeling.content.admin.repository.favorite;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteImage;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface FavoriteImageRepository extends JpaRepository<FavoriteImage, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "FavoriteImageRepository.findAll")})
    List<FavoriteImage> findAll();
}
