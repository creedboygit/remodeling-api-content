package com.hanssem.remodeling.content.admin.repository.budget;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetEstimate;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface BudgetEstimateAdminRepository extends JpaRepository<BudgetEstimate, Long> {
    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetEstimateAdminRepository.findById")})
    BudgetEstimate findById(long estimateMId);
}
