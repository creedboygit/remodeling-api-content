package com.hanssem.remodeling.content.admin.repository.goods;

import static com.hanssem.remodeling.content.domain.goods.entity.QGoods.goods;
import static com.hanssem.remodeling.content.domain.goods.entity.QGoodsCategory.goodsCategory;
import static com.hanssem.remodeling.content.domain.goods.entity.QGoodsKeywordStyle.goodsKeywordStyle;

import com.hanssem.remodeling.content.admin.controller.goods.request.GoodsSearchRequest;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.common.repository.CustomRepository;
import com.hanssem.remodeling.content.common.response.PageResponse;
import com.hanssem.remodeling.content.domain.goods.entity.Goods;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoodsAdminQueryRepository extends CustomRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public Page<Long> searchGoodsIds(GoodsSearchRequest request) {
        BooleanBuilder builder = makeQueryBuilder(request);

        List<Long> results = queryFactory
            .select(goods.id).distinct()
            .from(goods)
            .leftJoin(goods.categoryList, goodsCategory)
            .leftJoin(goods.keywordStyleList, goodsKeywordStyle)
            .where(builder)
            .orderBy(request.getOrderSpecifiers())
            .offset(request.getPageable().getOffset())
            .limit(request.getPageable().getPageSize())
            .fetch();

        //count
        long total =
            queryFactory
                .select(goods.id.countDistinct())
                .from(goods)
                .join(goods.categoryList, goodsCategory)
                .leftJoin(goods.keywordStyleList, goodsKeywordStyle)
                .where(builder)
                .fetch().get(0);

        return PageResponse.of(results, request.getPageable(), total);
    }

    public List<GoodsListResponse> findAllById(List<Long> resultIdList) {
        return queryFactory
            .select(Projections.fields(GoodsListResponse.class,
                goods.id.as("id")
                , goods.goodsName.as("goodsName")
                , goods.imagePathName.as("imagePathName")
                , goods.goodsBadgeTypeCode.as("goodsBadgeTypeCode")
                , goods.goodsStateCode.as("goodsStateCode")
                , goods.standardCategoryNo.as("standardCategoryNo")
                , goods.mallGoodsNo.as("mallGoodsNo")
                , goods.eventYn.as("eventYn")
                , goods.displayYn.as("displayYn")
                , goods.consultRequestYn.as("consultRequestYn")
                , goods.vrYn.as("vrYn")
                , goods.systemCreateDateTime.as("systemCreateDateTime")
                , goods.systemConstructorId.as("systemConstructorId")
                , goodsKeywordStyle.keywordName.as("styleCode")
                , ExpressionUtils.as(
                    JPAExpressions.select(goodsCategory.categoryLargeName.concat(">")
                        .concat(goodsCategory.categoryMiddleName).concat(">")
                        .concat(goodsCategory.categorySmallName).concat(">")
                        .concat(goodsCategory.categoryTinyName))
                        .from(goodsCategory)
                        .where(goodsCategory.goods.id.eq(goods.id), goods.standardCategoryNo.eq(goodsCategory.categoryNo)),
                    "standardCategoryName")
            ))
            .from(goods).distinct()
            .leftJoin(goods.categoryList, goodsCategory)
            .leftJoin(goods.keywordStyleList, goodsKeywordStyle)
            .where(goods.id.in(resultIdList))
            .fetch();
    }

    public PageResponse<GoodsListResponse> searchGoods(GoodsSearchRequest request) {

        BooleanBuilder builder = makeQueryBuilder(request);

        List<GoodsListResponse> results = queryFactory
            .select(Projections.fields(GoodsListResponse.class,
                goods.id.as("id")
                , goods.goodsName.as("goodsName")
                , goods.imagePathName.as("imagePathName")
                , goods.goodsBadgeTypeCode.as("goodsBadgeTypeCode")
                , goods.goodsStateCode.as("goodsStateCode")
                , goods.standardCategoryNo.as("standardCategoryNo")
                , ExpressionUtils.as(
                    JPAExpressions.select(goodsCategory.categoryLargeName.concat(">")
                                    .concat(goodsCategory.categoryMiddleName).concat(">")
                                    .concat(goodsCategory.categorySmallName).concat(">")
                                    .concat(goodsCategory.categoryTinyName))
                        .from(goodsCategory)
                        .where(goodsCategory.goods.id.eq(goods.id), goods.standardCategoryNo.eq(goodsCategory.categoryNo)),
                        "categoryName")
            )).distinct()
            .from(goods)
            .join(goods.categoryList, goodsCategory)
            .where(builder)
            .orderBy(request.getOrderSpecifiers())
            .offset(request.getPageable().getOffset())
            .limit(request.getPageable().getPageSize())
            .fetch();

        //count
        long total =
            queryFactory
                .select(goods.id.countDistinct())
                .from(goods)
                .join(goods.categoryList, goodsCategory)
                .where(builder)
                .fetch().get(0);

        return PageResponse.of(results, request.getPageable(), total);
    }



    private BooleanBuilder makeQueryBuilder(GoodsSearchRequest request) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(isNullIn(request.getGoodsIds(), goods.id));
        builder.and(isNullLike(request.getGoodsName(), goods.goodsName));
        builder.and(isNullEq(request.getGoodsStateCode(), goods.goodsStateCode));
        builder.and(isNullEq(request.getGoodsBadgeTypeCode(), goods.goodsBadgeTypeCode));
        builder.and(isNullEq(request.getDisplayYn(), goods.displayYn));
        builder.and(isNullEq(request.getEventYn(), goods.eventYn));
        builder.and(isNullEq(request.getVrYn(), goods.vrYn));
        builder.and(isNullEq(request.getConsultRequestYn(), goods.consultRequestYn));

        builder.and(isNullEq(request.getCategoryLargeNo(), goodsCategory.categoryLargeNo));
        builder.and(isNullEq(request.getCategoryMiddleNo(), goodsCategory.categoryMiddleNo));
        builder.and(isNullEq(request.getCategorySmallNo(), goodsCategory.categorySmallNo));
        builder.and(isNullEq(request.getCategoryTinyNo(), goodsCategory.categoryTinyNo));

        builder.and(isNullEq(request.getStyleCode(), goodsKeywordStyle.keywordName));


        return builder;
    }


    public List<Goods> findByIdList(List<Long> request) {
        return queryFactory
            .select(goods)
            .from(goods)
            .where(isNullIn(request, goods.id))
            .fetch();
    }
}
