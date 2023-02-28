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
public class AddFavoriteImageTagDto {
    private String imageName;
    private List<String> styles = new ArrayList<>();
    private List<String> colors = new ArrayList<>();
    private List<String> keywords = new ArrayList<>();
}
