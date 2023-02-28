package com.hanssem.remodeling.content.api.repository.budget;

import static com.hanssem.remodeling.content.domain.budget.entity.QBudgetEstimate.budgetEstimate;
import static com.hanssem.remodeling.content.domain.budget.entity.QBudgetEstimateInfo.budgetEstimateInfo;
import static com.hanssem.remodeling.content.domain.budget.entity.QBudgetStyle.budgetStyle;

import com.hanssem.remodeling.content.common.repository.CustomRepository;
import com.hanssem.remodeling.content.domain.budget.entity.BudgetEstimateInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BudgetEstimateInfoQueryRepository extends CustomRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public List<BudgetEstimateInfo> findByBudgetEstimateMIdAndUserId(long estimateMId, long AuthUserId) {
        List<BudgetEstimateInfo> results = queryFactory
                .select(budgetEstimateInfo)
                .from(budgetEstimateInfo)
                .innerJoin(budgetEstimateInfo.budgetEstimate , budgetEstimate)
                .innerJoin(budgetEstimateInfo.budgetStyle, budgetStyle)
                .where(
                        budgetEstimateInfo.budgetEstimateMId.eq(estimateMId),
                        budgetEstimate.userId.eq(AuthUserId)
                )
                .orderBy(budgetStyle.displaySeq.asc())
                .fetch();

        return results;
    }
}
