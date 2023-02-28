package com.hanssem.remodeling.content.api.service.budget.vo;

import com.hanssem.remodeling.content.domain.budget.entity.BudgetStyleMaterial;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BudgetStyleVo {
    private Long budgetPtypMId;

    private String spaceType;

    private String styleName;

    private String styleTitle;

    private String styleMainImagePath;

    private String styleSubImagePath;

    private int displaySeq;

    private String useYn;

    @Builder.Default
    private List<BudgetStyleMaterial> budgetStyleMaterialList = new ArrayList<>();
}
