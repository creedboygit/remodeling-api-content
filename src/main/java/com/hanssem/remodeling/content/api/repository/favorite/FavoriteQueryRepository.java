package com.hanssem.remodeling.content.api.repository.favorite;

import static com.hanssem.remodeling.content.domain.favorite.entity.QFavoriteStyle.favoriteStyle;
import static com.hanssem.remodeling.content.domain.favorite.entity.QFavoriteImage.favoriteImage;
import static com.hanssem.remodeling.content.domain.favorite.entity.QFavoriteImageTag.favoriteImageTag;
import static com.hanssem.remodeling.content.domain.favorite.entity.QFavoriteStyleTag.favoriteStyleTag;
import static com.hanssem.remodeling.content.domain.favorite.entity.QFavoriteTag.favoriteTag;

import com.hanssem.remodeling.content.api.controller.favorite.request.FavoriteTestRequest;
import com.hanssem.remodeling.content.api.service.favorite.StyleGroupDto;
import com.hanssem.remodeling.content.common.repository.CustomRepository;
import com.hanssem.remodeling.content.constant.FavoriteTagType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FavoriteQueryRepository extends CustomRepository {

    private final JPAQueryFactory queryFactory;
    private String string;

    public List<StyleGroupDto> findByImageStyle(FavoriteTestRequest request) {

        List<StyleGroupDto> results = queryFactory
            .select(Projections.fields(StyleGroupDto.class,
                favoriteTag.tagName.as("styleCode")
            ))
            .from(favoriteImage)
            .join(favoriteImageTag).on(favoriteImage.id.eq(favoriteImageTag.favoriteImageId))
            .join(favoriteTag).on(favoriteTag.id.eq(favoriteImageTag.favoriteTagId))
            .where(
                isNullIn(request.getImages(), favoriteImage.id),
                isNullEq(FavoriteTagType.STYLE, favoriteTag.tagType)
            )
            .fetch();

        return results;
    }

    /**
     *  -- tag로 style type select
     * 	select sm.style_code as style
     * 	from tb_favorite_tag_m tm
     * 	join tb_favorite_style_tag_d st on tm.favorite_tag_id = st.favorite_tag_id
     * 	join tb_favorite_style_m sm on sm.favorite_style_id = st.favorite_style_id
     *     where tm.favorite_tag_id in (75, 76, 10, 20, 30)
     */
    public List<StyleGroupDto> findByStyle(FavoriteTestRequest request) {

        List<Long> allTagsId = new ArrayList<>();
        allTagsId.addAll(request.getColors());
        allTagsId.addAll(request.getKeywords());

        List<StyleGroupDto> results = queryFactory
            .select(Projections.fields(StyleGroupDto.class,
                favoriteStyle.styleCode.as("styleCode")
            ))
            .from(favoriteTag)
            .join(favoriteStyleTag).on(favoriteTag.id.eq(favoriteStyleTag.favoriteTagId))
            .join(favoriteStyle).on(favoriteStyle.id.eq(favoriteStyleTag.favoriteStyle.id))
            .where(
                isNullIn(allTagsId, favoriteTag.id)
            )
            .fetch();

        return results;
    }

    private BooleanBuilder makeFindByImageQueryBuilder(FavoriteTestRequest request) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(isNullIn(request.getImages(), favoriteImage.id));
        builder.and( isNullEq(FavoriteTagType.STYLE, favoriteTag.tagType));

        return builder;
    }

}
