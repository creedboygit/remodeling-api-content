package com.hanssem.remodeling.content.admin.service.goods.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class AddGoodsDto {

    private Long id;
    private String goodsName;
    private String goodsGroupCode;
    private String goodsStateCode;
    private LocalDateTime saleBeginDateTime;
    private LocalDateTime saleEndDateTime;
    private String displayYn;
    private int standardCategoryNo;
    private String consultRequestYn; //10
    private String eventYn;
    private String vrYn;
    private String goodsBadgeTypeCode;
    private String salesShopTypeCode;
    private int mallGoodsNo;
    private String imagePath;
    private String videoUrl;
    private String goodsOutline;
    private String searchKeyword;
    private String description; //20
    private String recommendContent;
    private String recommendTag;
    private String webPageContent;
    private String mobilePageContent;

    private LocalDateTime systemCreateDateTime;
    private String systemConstructorId;
    private LocalDateTime systemUpdateDateTime;
    private String systemUpdaterId; //28


    public List<String> getKeywordList() {
        if (searchKeyword == null || searchKeyword.isEmpty()) return new ArrayList<String>();
        return Arrays.asList(searchKeyword.split("\\s*,\\s*"));
    }

    public List<String> getSalesShopTypeCodeList() {
        if (salesShopTypeCode == null || salesShopTypeCode.isEmpty()) return new ArrayList<String>();
        return Arrays.asList(salesShopTypeCode.split("\\s*,\\s*"));
    }
}
