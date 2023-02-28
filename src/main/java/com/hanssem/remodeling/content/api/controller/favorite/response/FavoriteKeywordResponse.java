package com.hanssem.remodeling.content.api.controller.favorite.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteKeywordResponse {
    private Long id;
    private String tagName;
    @JsonIgnore
    private String imagePath;
    private String imageUrl;

    public String getImageUrl() {
        return GlobalConstants.makeCdnUrl(imagePath);
    }
}
