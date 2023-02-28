package com.hanssem.remodeling.content.api.repository.budget;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyleMaterial;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface BudgetStyleMaterialRepository extends JpaRepository<BudgetStyleMaterial, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetStyleMaterialRepository.findByBudgetStyleMIdIn")})
    @Query("SELECT bsm from BudgetStyleMaterial bsm where bsm.budgetStyleMId in (:budgetStyleMIds)")
    List<BudgetStyleMaterial> findByBudgetStyleMIdIn(
            @Param("budgetStyleMIds") List<Long> budgetStyleId);
}
