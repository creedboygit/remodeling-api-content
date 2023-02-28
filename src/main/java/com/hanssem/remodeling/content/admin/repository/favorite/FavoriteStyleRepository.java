package com.hanssem.remodeling.content.admin.repository.favorite;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteStyle;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface FavoriteStyleRepository extends JpaRepository<FavoriteStyle, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "FavoriteStyleRepository.findByStyleCode")})
    @Query("select s from FavoriteStyle s where s.styleCode = :styleCode")
    FavoriteStyle findByStyleCode(@Param("styleCode") String styleCode);
}
