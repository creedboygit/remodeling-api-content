package com.hanssem.remodeling.content.api.repository.budget;

import static com.hanssem.remodeling.content.domain.budget.entity.QBudgetStyle.budgetStyle;

import com.hanssem.remodeling.content.constant.SpaceDetailType;
import com.hanssem.remodeling.content.constant.SpaceType;
import com.hanssem.remodeling.content.common.repository.CustomRepository;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyle;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BudgetStyleQueryRepository extends CustomRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public List<BudgetStyle> findByPtypId(long ptypMId) {
        List<BudgetStyle> results = queryFactory
                .select(budgetStyle)
                .from(budgetStyle)
                .where(
                        budgetStyle.budgetPtypMId.eq(ptypMId),
                        budgetStyle.useYn.eq("Y")
                )
                .orderBy(budgetStyle.displaySeq.asc())
                .fetch();

        return results;
    }

    public List<BudgetStyle> findByPtypIdAndSpaceType(long ptypMId, SpaceType spaceType) {
        List<BudgetStyle> results = queryFactory
            .select(budgetStyle)
            .from(budgetStyle)
            .where(
                budgetStyle.budgetPtypMId.eq(ptypMId),
                budgetStyle.spaceType.eq(spaceType),
                budgetStyle.useYn.eq("Y")
            )
                .orderBy(budgetStyle.displaySeq.asc())
            .fetch();

        return results;
    }

    public List<BudgetStyle> findByPtypIdAndSpaceTypeAndSpaceDetailType(long ptypMId, SpaceType spaceType, SpaceDetailType spaceDetailType) {
        List<BudgetStyle> results = queryFactory
            .select(budgetStyle)
            .from(budgetStyle)
            .where(
                budgetStyle.budgetPtypMId.eq(ptypMId),
                budgetStyle.spaceType.eq(spaceType),
                budgetStyle.spaceDetailType.eq(spaceDetailType),
                budgetStyle.useYn.eq("Y")
            )
                .orderBy(budgetStyle.displaySeq.asc())
            .fetch();

        return results;
    }

}
