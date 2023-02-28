package com.hanssem.remodeling.content.admin.service.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsCategoryDto {
    private int categoryLargeNo;
    private int categoryMiddleNo;
    private int categorySmallNo;
    private int categoryTinyNo;
    private String categoryLargeName;
    private String categoryMiddleName;
    private String categorySmallName;
    private String categoryTinyName;

    public GoodsCategoryDto(
        int categoryLargeNo, int categoryMiddleNo, int categorySmallNo, int categoryTinyNo,
        String categoryLargeName, String categoryMiddleName, String categorySmallName, String categoryTinyName) {
        this.categoryLargeNo = categoryLargeNo;
        this.categoryMiddleNo = categoryMiddleNo;
        this.categorySmallNo = categorySmallNo;
        this.categoryTinyNo = categoryTinyNo;
        this.categoryLargeName = categoryLargeName;
        this.categoryMiddleName = categoryMiddleName;
        this.categorySmallName = categorySmallName;
        this.categoryTinyName = categoryTinyName;
    }

    public GoodsCategoryDto(int categoryLargeNo, int categoryMiddleNo, int categorySmallNo, int categoryTinyNo) {
        this.categoryLargeNo = categoryLargeNo;
        this.categoryMiddleNo = categoryMiddleNo;
        this.categorySmallNo = categorySmallNo;
        this.categoryTinyNo = categoryTinyNo;
    }
}
