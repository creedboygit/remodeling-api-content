package com.hanssem.remodeling.content.api.controller.favorite.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteStyleImageResponse {
    @JsonIgnore
    private String imagePath;

    private String imageUrl;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePath);
    }
}
