package com.hanssem.remodeling.content.domain.budget.entity;

import com.hanssem.remodeling.content.constant.SpaceType;
import com.hanssem.remodeling.content.domain.SystemEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_budget_pntp_option_d")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BudgetPtypOption extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_PNTP_OPTION_ID")
    private Long id;

    @Column(name = "BUDGET_PNTP_ID")
    private Long budgetPtypMId;

    @Column(name = "BUDGET_SPACE_TYPE")
    @Enumerated(value = EnumType.STRING)
    private SpaceType budgetSpaceType;

    @Column(name = "SPACE_COUNT")
    private int count;

}
