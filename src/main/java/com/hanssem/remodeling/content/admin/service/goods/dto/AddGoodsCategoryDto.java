package com.hanssem.remodeling.content.admin.service.goods.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddGoodsCategoryDto {
    private Long goodsId;
    private int categoryNo;
    private int categoryLargeNo;
    private int categoryMiddleNo;
    private int categorySmallNo;
    private int categoryTinyNo;
    private String categoryLargeName;
    private String categoryMiddleName;
    private String categorySmallName;
    private String categoryTinyName;
    private int categoryDisplaySeq;
    private String useYn;

    private LocalDateTime systemCreateDateTime;
    private String systemConstructorId;
    private LocalDateTime systemUpdateDateTime;
    private String systemUpdaterId;
}
