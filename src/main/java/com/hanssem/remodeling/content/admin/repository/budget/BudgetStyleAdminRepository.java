package com.hanssem.remodeling.content.admin.repository.budget;

import static org.hibernate.annotations.QueryHints.COMMENT;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyle;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface BudgetStyleAdminRepository extends JpaRepository<BudgetStyle, Long> {
//    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetStyleAdminRepository.saveAllAndFlush")})
//    void saveAllAndFlush(List<BudgetStyle> budgetStyleList);

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetStyleAdminRepository.findAll")})
    List<BudgetStyle> findAll();

}
