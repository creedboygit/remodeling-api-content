package com.hanssem.remodeling.content.domain.budget.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.domain.SystemEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_budget_estimate_information_d")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BudgetEstimateInfo extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_ESTIMATE_INFORMATION_ID")
    private Long id;

    @Column(name = "BUDGET_ESTIMATE_ID")
    private Long budgetEstimateMId;

    @Column(name = "BUDGET_STYLE_ID", updatable = false)
    private Long budgetStyleMId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BUDGET_STYLE_ID", insertable = false, updatable = false)
    private BudgetStyle budgetStyle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUDGET_ESTIMATE_ID", insertable = false, updatable = false)
    @JsonIgnore
    private BudgetEstimate budgetEstimate;
}
