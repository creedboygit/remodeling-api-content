package com.hanssem.remodeling.content.domain.budget.entity;

import com.hanssem.remodeling.content.constant.SpaceDetailType;
import com.hanssem.remodeling.content.constant.SpaceType;
import com.hanssem.remodeling.content.domain.SystemEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "tb_budget_style_m")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BudgetStyle extends SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_STYLE_ID")
    private Long id;

    @Column(name = "BUDGET_PNTP_ID")
    private Long budgetPtypMId;

    @Column(name = "BUDGET_SPACE_TYPE")
    @Enumerated(value = EnumType.STRING)
    private SpaceType spaceType;

    @Column(name = "BUDGET_SPACE_DETAIL_TYPE")
    @Enumerated(value = EnumType.STRING)
    private SpaceDetailType spaceDetailType;

    @Column(name = "STYLE_NAME")
    private String styleName;

    @Column(name = "STYLE_TITLE")
    private String styleTitle;

    @Column(name = "STYLE_MAIN_IMAGE_PATH")
    private String styleMainImagePath;

    @Column(name = "STYLE_SUB_IMAGE_PATH")
    private String styleSubImagePath;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "DISPLAY_SEQ")
    private int displaySeq;

    @Builder.Default
    @OneToMany(mappedBy = "budgetStyle", fetch = FetchType.LAZY)
    private List<BudgetEstimateInfo> budgetEstimateInfoList = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BUDGET_STYLE_ID", insertable = false, updatable = false)
    private List<BudgetStyleMaterial> budgetStyleMaterialList = new ArrayList<>();


    // 스타일 사용여부 수정 메서드
    public void changeUseYn(String useYn) {
        this.useYn = useYn;
    }
    // 스타일
    public void changeImagePath(String styleMainImagePath , String styleSubImagePath) {
        this.styleMainImagePath = styleMainImagePath;
        this.styleSubImagePath = styleSubImagePath;
    }
}