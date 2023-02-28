package com.hanssem.remodeling.content.api.controller.budget.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "PtypStyleResponse", description = "평형별 스타일 조회")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class PtypStyleResponse implements Serializable {

    @Schema(name = "spaceName", title = "공간명", type = "String", example = "방")
    @NotBlank
    private String spaceName;

    @Schema(name = "spaceType", title = "공간코드", type = "String", example = "ROOM")
    @NotBlank
    private String spaceType;

    private List<StyleList> style;

    @Schema(title = "StyleList", description = "스타일리스트")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class StyleList {

        @Schema(name = "styleMId", title = "스타일아이디", type = "Long", example = "7")
        @NotBlank
        private Long styleMId;

        @Schema(name = "spaceDetailName", title = "공간상세명", type = "String", example = "침실")
        @NotBlank
        private String spaceDetailName;

        @Schema(name = "spaceDetailType", title = "공간상세타입", type = "String", example = "BED_ROOM")
        @NotBlank
        private String spaceDetailType;

        @Schema(name = "styleName", title = "스타일명", type = "String", example = "리브라이크어포엠")
        @NotBlank
        private String styleName;

        @Schema(name = "styleTitle", title = "스타일타이틀", type = "String", example = "본연의 기능을 살리며 미니멀하게 연출한 침실")
        @NotBlank
        private String styleTitle;

        @Schema(name = "styleMainImageUrl", title = "스타일메인이미지URL", type = "String", example = "https://dev-static.remodeling.hanssem.com/content/budget/style/main.jpg")
        @NotBlank
        private String styleMainImageUrl;

        @Schema(name = "styleSubImageUrl", title = "스타일서브이미지URL", type = "String", example = "https://dev-static.remodeling.hanssem.com/content/budget/style/sub.jpg")
        @NotBlank
        private String styleSubImageUrl;

        @Schema(name = "totalAmount", title = "가격", type = "Integer", example = "10000000")
        @NotBlank
        private int totalAmount;

        @Schema(name = "displaySeq", title = "디스플레이순번", type = "integer", example = "7")
        @NotBlank
        private int displaySeq;

        public String getStyleMainImageUrl() {
            return GlobalConstants.makeCdnUrl(styleMainImageUrl);
        }

        public String getStyleSubImageUrl() {
            return GlobalConstants.makeCdnUrl(styleSubImageUrl);
        }

    }
}


