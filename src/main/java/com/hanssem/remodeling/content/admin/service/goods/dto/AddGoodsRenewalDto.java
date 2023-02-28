package com.hanssem.remodeling.content.admin.service.goods.dto;

import com.hanssem.remodeling.content.common.model.IsYn;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
public class AddGoodsRenewalDto {
    private Long id; //0
    private String recommendContent; //3
    private Set<String> recommendTags; //4~8
    private String goodsStyleCode; //9
    private String goodsBadgeTypeCode; //11
    private String consultRequestYn; //12
    private Set<String> searchKeyword; //13
}