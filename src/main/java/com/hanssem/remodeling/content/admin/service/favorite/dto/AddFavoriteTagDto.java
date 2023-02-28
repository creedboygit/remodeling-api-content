package com.hanssem.remodeling.content.admin.service.favorite.dto;

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
public class AddFavoriteTagDto {
    private String tagName;
    private String tagType ;
    private String imagePath;
    private String etc;
}
