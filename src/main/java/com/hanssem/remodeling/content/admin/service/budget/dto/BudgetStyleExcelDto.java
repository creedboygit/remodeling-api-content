package com.hanssem.remodeling.content.admin.service.budget.dto;

import com.hanssem.remodeling.content.constant.ConstructionFieldType;
import com.hanssem.remodeling.content.constant.ConstructionType;
import com.hanssem.remodeling.content.constant.SpaceDetailType;
import com.hanssem.remodeling.content.constant.SpaceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BudgetStyleExcelDto {

    public static final int ADMIN_LAYOUT_EXCEL_START_ROWNUM = 1;

    private int ptypValue;
    private String styleName;
    private SpaceType spaceType;
    private SpaceDetailType spaceDetailType;
    private ConstructionType constructionType;
    private ConstructionFieldType constructionFieldType;
    private String materialName;
    private int materialAmount;
    private int constructionAmount;
    private int totalAmount;
    private String styleTitle;
    private String styleMainImagePathValue;
    private String styleSubImagePathValue;

    @Getter
    public enum LayoutCellEnum {
        PTYP_VALUE(0),
        STYLE_NAME(1),
        SPACE_TYPE(2),
        SPACE_DETAIL_TYPE(3),
        CONSTRUCTION_TYPE(4),
        CONSTRUCTION_FILED_TYPE(5),
        MATERIAL_NAME(6),
        MATERIAL_AMOUNT(7),
        CONSTRUCTION_AMOUNT(8),
        TOTAL_AMOUNT(9),
        STYLE_TITLE(10),
        STYLE_MAIN_IMAGE_PATH_VALUE(11),
        STYLE_SUB_IMAGE_PATH_VALUE(12);

        int cellNumber;

        LayoutCellEnum(int seq) {
            this.cellNumber = seq;
        }
    }
}
