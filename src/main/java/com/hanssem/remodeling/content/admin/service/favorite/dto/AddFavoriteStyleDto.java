package com.hanssem.remodeling.content.admin.service.favorite.dto;

import java.util.ArrayList;
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
public class AddFavoriteStyleDto {

    private String manageName;
    private String styleName;
    private String styleCode;
    private String description;

    private List<String> images = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

}
