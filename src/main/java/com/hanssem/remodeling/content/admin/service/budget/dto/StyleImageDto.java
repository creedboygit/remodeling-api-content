package com.hanssem.remodeling.content.admin.service.budget.dto;

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
public class StyleImageDto {
    private Long styleId;
    private String styleMainImagePathValue;
    private String styleSubImagePathValue;
}
