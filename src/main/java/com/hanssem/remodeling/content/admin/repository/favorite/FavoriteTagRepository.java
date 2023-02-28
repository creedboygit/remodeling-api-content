package com.hanssem.remodeling.content.admin.repository.favorite;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.constant.FavoriteTagType;
import com.hanssem.remodeling.content.domain.favorite.entity.FavoriteTag;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface FavoriteTagRepository extends JpaRepository<FavoriteTag, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "FavoriteTagRepository.findByTagName")})
    @Query("select t from FavoriteTag t where t.tagName = :tagName")
    FavoriteTag findByTagName(@Param("tagName") String tagName);

    @QueryHints({@QueryHint(name = COMMENT, value = "FavoriteTagRepository.findByAllTags")})
    @Query("select t from FavoriteTag t where t.tagType = :tagType")
    List<FavoriteTag> findByAllTags(@Param("tagType") FavoriteTagType tagType);

}
