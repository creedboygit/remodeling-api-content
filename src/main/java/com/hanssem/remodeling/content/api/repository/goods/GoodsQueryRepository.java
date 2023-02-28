package com.hanssem.remodeling.content.api.repository.goods;

import com.hanssem.remodeling.content.api.controller.goods.request.GoodsRequest;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.api.controller.goods.response.GoodsSimpleResponse;
import com.hanssem.remodeling.content.common.repository.CustomRepository;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.QueryHint;
import java.util.List;

import static com.hanssem.remodeling.content.domain.goods.entity.QGoods.goods;
import static com.hanssem.remodeling.content.domain.goods.entity.QGoodsCategory.goodsCategory;
import static com.hanssem.remodeling.content.domain.goods.entity.QGoodsKeywordStyle.goodsKeywordStyle;
import static com.hanssem.remodeling.content.domain.goods.entity.QGoodsKeywordRelatedStyle.goodsKeywordRelatedStyle;
import static org.hibernate.annotations.QueryHints.COMMENT;

@Repository
@RequiredArgsConstructor
public class GoodsQueryRepository extends CustomRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    /**
     * 인테리어 취향 테스트 > 추천상품 목록 조회
     */
    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsQueryRepository.findByStyleCode")})
    public List<GoodsSimpleResponse> findByStyleCode(String styleCode) {
        List<GoodsSimpleResponse> results = queryFactory
            .select(Projections.fields(GoodsSimpleResponse.class,
                goods.id.as("id")
                , goods.goodsName.as("goodsName")
                , goods.imagePathName.as("imagePathName")
                , goods.goodsBadgeTypeCode.as("goodsBadgeTypeCode")
                , goods.goodsStateCode.as("goodsStateCode")
                , goods.vrYn.as("vrYn")
            ))
            .from(goods)
            .join(goods.keywordStyleList, goodsKeywordStyle)
            .where(
                isNullEq("N", goods.goodsStateCode),
                isNullEq(styleCode, goodsKeywordStyle.keywordName)
            )
            .limit(4L)
            .fetch();

        return results;
    }

    /**
     * 인테리어 취향 테스트 > 연관상품 목록 조회
     */
    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsQueryRepository.findByRelatedStyleCode")})
    public List<GoodsSimpleResponse> findByRelatedStyleCode(String styleCode) {
        List<GoodsSimpleResponse> results = queryFactory
            .select(Projections.fields(GoodsSimpleResponse.class,
                goods.id.as("id")
                , goods.goodsName.as("goodsName")
                , goods.imagePathName.as("imagePathName")
                , goods.goodsBadgeTypeCode.as("goodsBadgeTypeCode")
                , goods.goodsStateCode.as("goodsStateCode")
                , goods.vrYn.as("vrYn")
            ))
            .from(goods)
            .join(goods.keywordRelatedStyleList, goodsKeywordRelatedStyle)
            .where(
                isNullEq("N", goods.goodsStateCode),
                isNullEq(styleCode, goodsKeywordRelatedStyle.keywordName)
            )
            .limit(4L)
            .fetch();

        return results;
    }

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsQueryRepository.findAllById")})
    public List<GoodsListResponse> findAllById(List<Long> resultIdList, GoodsRequest request) {

        List<GoodsListResponse> results = queryFactory
                .select(Projections.fields(GoodsListResponse.class,
                        goods.id.as("id")
                        , goods.goodsName.as("goodsName")
                        , goods.imagePathName.as("imagePathName")
                        , goods.goodsBadgeTypeCode.as("goodsBadgeTypeCode")
                        , goods.goodsStateCode.as("goodsStateCode")
                ))
                .from(goods).distinct()
                .join(goods.categoryList, goodsCategory)
                .where(goods.id.in(resultIdList))
                .orderBy(new CaseBuilder().when(goods.goodsBadgeTypeCode.isEmpty()).then(1).otherwise(0).asc())
                .orderBy(goods.goodsBadgeTypeCode.asc())
                .orderBy(request.getOrderSpecifiers().length < 1 ?
                        getDefaultOrderSpecifiers(Order.DESC, "goods.id") : request.getOrderSpecifiers())
                .fetch();

        return results;
    }

    @QueryHints({@QueryHint(name = COMMENT, value = "GoodsQueryRepository.getGoodsIds")})
    public Page<Long> getGoodsIds(GoodsRequest request) {

        BooleanBuilder builder = makeQueryBuilder(request);

        List<Long> result = queryFactory
                .select(goods.id).distinct()
                .from(goods)
                .join(goods.categoryList, goodsCategory)
                .where(builder)
                .orderBy(new CaseBuilder().when(goods.goodsBadgeTypeCode.isEmpty()).then(1).otherwise(0).asc())
                .orderBy(goods.goodsBadgeTypeCode.asc())
                .orderBy(request.getOrderSpecifiers().length < 1 ?
                        getDefaultOrderSpecifiers(Order.DESC, "goods.id") : request.getOrderSpecifiers())
                .offset(request.getPageable().getOffset())
                .limit(request.getPageable().getPageSize())
                .fetch();

        long total = queryFactory
                .select(goods.id.countDistinct())
                .from(goods)
                .join(goods.categoryList, goodsCategory)
                .where(builder)
                .fetch().get(0);

        return PageResponse.of(result, request.getPageable(), total);
    }

    private BooleanBuilder makeQueryBuilder(GoodsRequest request) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(isNullEq(request.getDisplayYn(), goods.displayYn));
        builder.and(isNullEq(request.getGoodsStateCode(), goods.goodsStateCode));
        builder.and(isNullEq(request.getGoodsBadgeTypeCode(), goods.goodsBadgeTypeCode));
        builder.and(isNullEq(request.getCategoryLargeNo(), goodsCategory.categoryLargeNo));
        builder.and(isNullEq(request.getCategoryMiddleNo(), goodsCategory.categoryMiddleNo));
        builder.and(isNullEq(request.getCategorySmallNo(), goodsCategory.categorySmallNo));

        return builder;
    }
}
