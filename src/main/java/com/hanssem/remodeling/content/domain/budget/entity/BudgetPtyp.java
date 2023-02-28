package com.hanssem.remodeling.content.domain.budget.entity;

import com.hanssem.remodeling.content.domain.SystemEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_budget_pntp_m")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BudgetPtyp extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_PNTP_ID")
    private Long id;

    @Column(name = "PNTP_VALUE")
    private int ptypValue;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "BUDGET_PNTP_ID", insertable = false, updatable = false)
    private List<BudgetPtypOption> budgetPtypOptionList = new ArrayList<>();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "BUDGET_PNTP_ID", insertable = false, updatable = false)
    private List<BudgetStyle> budgetStyleList = new ArrayList<>();
}
