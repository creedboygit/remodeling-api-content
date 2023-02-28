package com.hanssem.remodeling.content.admin.repository.budget;

import static org.hibernate.annotations.QueryHints.*;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetPtyp;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface BudgetPtypAdminRepository extends JpaRepository<BudgetPtyp, Long> {
    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetPtypAdminRepository.findByPtypValue")})
    BudgetPtyp findByPtypValue(int ptypValue);
}
