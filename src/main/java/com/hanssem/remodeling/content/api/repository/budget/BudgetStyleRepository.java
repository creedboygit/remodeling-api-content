package com.hanssem.remodeling.content.api.repository.budget;

import static org.hibernate.annotations.QueryHints.*;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyle;
import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;

public interface BudgetStyleRepository extends JpaRepository<BudgetStyle, Long> {
    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetStyleRepository.findById")})
    BudgetStyle findById(long styleMId);

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetStyleRepository.findByIdIn")})
    List<BudgetStyle> findByIdIn(List<Long> styleIdList);

    @QueryHints({@QueryHint(name = COMMENT, value = "BudgetStyleRepository.findByIdInOrderByDisplaySeqAsc")})
    List<BudgetStyle> findByIdInOrderByDisplaySeqAsc(List<Long> styleIdList);
}