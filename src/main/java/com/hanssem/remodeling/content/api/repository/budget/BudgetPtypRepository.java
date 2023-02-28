package com.hanssem.remodeling.content.api.repository.budget;

import static org.hibernate.annotations.QueryHints.*;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetPtyp;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

public interface BudgetPtypRepository extends JpaRepository<BudgetPtyp, Long> {

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetPtypRepository.findAll")})
    List<BudgetPtyp> findAll();
}
