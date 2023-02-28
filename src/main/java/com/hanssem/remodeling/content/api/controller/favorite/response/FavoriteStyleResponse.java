package com.hanssem.remodeling.content.api.controller.favorite.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteStyleResponse {

    private Long id;

    private String manageName;

    private String styleName;

    private String styleCode;

    private String description;

    private List<String> styleImages = new ArrayList<>();

    public List<String> getStyleImages() {
        if (Objects.isNull(styleImages) || styleImages.isEmpty()) return null;

        List<String> resultImages = new ArrayList<>();
        for (String styleImage : styleImages) {
            resultImages.add(GlobalConstants.makeCdnUrl(styleImage));
        }

        return resultImages;
    }

}
