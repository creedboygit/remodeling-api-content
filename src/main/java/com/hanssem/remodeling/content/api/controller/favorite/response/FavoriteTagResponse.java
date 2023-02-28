package com.hanssem.remodeling.content.api.controller.favorite.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteTagResponse {
    private Long id;
    private String tagName;
    private String imagePath;
    private String imageUrl;
    private String color;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePath);
    }

}
