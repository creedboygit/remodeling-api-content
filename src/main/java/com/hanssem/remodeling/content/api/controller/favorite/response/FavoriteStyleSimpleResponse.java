package com.hanssem.remodeling.content.api.controller.favorite.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteStyleSimpleResponse {

    @Schema(title = "id", description = "스타일 id", type = "Long", example = "1")
    private Long id;
    @Schema(title = "styleCode", description = "스타일코드", type = "String", example = "MODERN")
    private String styleCode;
    @Schema(title = "title", description = "스타일명", type = "String", example = "PURE MODERN")
    private String title;
    @Schema(title = "thumbUrl", description = "섬네일이미지", type = "String", example = "https://image.hanssem.com/hsimg//gds/550/618/618447_A1.jpg", required = true)
    private String thumbUrl;

    public String getThumbUrl() {
        return GlobalConstants.makeCdnUrl(thumbUrl);
    }
}

