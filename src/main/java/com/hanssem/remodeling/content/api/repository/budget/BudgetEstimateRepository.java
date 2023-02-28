package com.hanssem.remodeling.content.api.repository.budget;

import static org.hibernate.annotations.QueryHints.*;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetEstimate;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface BudgetEstimateRepository extends JpaRepository<BudgetEstimate, Long> {
    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetEstimateRepository.delete")})
    void delete(BudgetEstimate budgetEstimate);

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetEstimateRepository.findByUserIdOrderByIdDesc")})
    List<BudgetEstimate> findByUserIdOrderByIdDesc(Long userId);

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetEstimateRepository.findByIdAndUserId")})
    BudgetEstimate findByIdAndUserId(long estimateMId, long userId);
}
