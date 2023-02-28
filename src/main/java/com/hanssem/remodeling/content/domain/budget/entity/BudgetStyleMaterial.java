package com.hanssem.remodeling.content.domain.budget.entity;

import com.hanssem.remodeling.content.constant.ConstructionFieldType;
import com.hanssem.remodeling.content.constant.ConstructionType;
import com.hanssem.remodeling.content.domain.SystemEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "tb_budget_style_material_d")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BudgetStyleMaterial extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_STYLE_MATERIAL_ID")
    private Long id;

    @Column(name = "BUDGET_STYLE_ID")
    private Long budgetStyleMId;

    @Column(name = "BUDGET_CONSTRUCTION_TYPE")
    @Enumerated(value = EnumType.STRING)
    private ConstructionType constructionType;

    @Column(name = "BUDGET_CONSTRUCTION_FIELD_TYPE")
    @Enumerated(value = EnumType.STRING)
    private ConstructionFieldType constructionFieldType;

    @Column(name = "MATERIAL_NAME")
    private String materialName;

    @Column(name = "MATERIAL_AMOUNT")
    private int materialAmount;

    @Column(name = "CONSTRUCTION_AMOUNT")
    private int constructionAmount;

    @Column(name = "TOTAL_MATERIAL_AMOUNT")
    private int totalAmount;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BUDGET_STYLE_ID", insertable = false, updatable = false)
    private BudgetStyle budgetStyle;
}
