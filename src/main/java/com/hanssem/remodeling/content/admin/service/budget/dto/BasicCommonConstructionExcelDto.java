package com.hanssem.remodeling.content.admin.service.budget.dto;

import com.hanssem.remodeling.content.constant.ConstructionFieldType;
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
public class BasicCommonConstructionExcelDto {

    public static final int ADMIN_LAYOUT_EXCEL_START_ROWNUM = 1;

    private int ptypValue;
    private ConstructionFieldType constructionFieldType;
    private String materialName;
    private int materialAmount;
    private int constructionAmount;
    private int totalAmount;

    @Getter
    public enum BasicCommonConstructionLayoutCellEnum {
        PTYP_VALUE(0),
        CONSTRUCTION_FILED_TYPE(1),
        MATERIAL_NAME(2),
        MATERIAL_AMOUNT(3),
        CONSTRUCTION_AMOUNT(4),
        TOTAL_AMOUNT(5);

        int cellNumber;

        BasicCommonConstructionLayoutCellEnum(int seq) {
            this.cellNumber = seq;
        }
    }
}
