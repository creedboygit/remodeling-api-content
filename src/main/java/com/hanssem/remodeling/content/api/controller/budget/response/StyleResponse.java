package com.hanssem.remodeling.content.api.controller.budget.response;

import com.hanssem.remodeling.content.common.util.GlobalConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "StyleResponse", description = "선택한 스타일의 정보")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class StyleResponse {

    @Schema(name = "styleMId", title = "스타일아이디", type = "Long", example = "7")
    @NotBlank
    private Long styleMId;

    @Schema(name = "spaceName", title = "공간명", type = "String", required = true, example = "방")
    @NotBlank
    private String spaceName;

    @Schema(name = "spaceDetailName", title = "공간상세명", type = "String", required = true, example = "침실")
    private String spaceDetailName;

    @Schema(name = "styleTitle", title = "스타일타이틀", type = "String", example = "본연의 기능을 살리며 미니멀하게 연출한 침실")
    private String styleTitle;

    @Schema(name = "styleMainImageUrl", title = "스타일메인이미지URL", type = "String", example = "https://dev-static.remodeling.hanssem.com/content/budget/style/main.jpg")
    private String styleMainImageUrl;

    @Schema(name = "styleSubImageUrl", title = "스타일서브이미지URL", type = "String", example = "https://dev-static.remodeling.hanssem.com/content/budget/style/sub.jpg")
    private String styleSubImageUrl;

    private CommonConstructionList commonConstruction;

    private SpaceConstructionList spaceConstruction;

    public String getStyleMainImageUrl() {
        return GlobalConstants.makeCdnUrl(styleMainImageUrl);
    }

    public String getStyleSubImageUrl() {
        return GlobalConstants.makeCdnUrl(styleSubImageUrl);
    }

    @Schema(title = "CommonConstructionList", description = "공통공사")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class CommonConstructionList {

        @Schema(name = "totalCommonAmount", title = "총 공통공사 금액", type = "Integer", example = "10000000")
        @NotBlank
        private int totalCommonAmount;

        private List<ConstrcutionProcess> process;
    }

    @Schema(title = "SpaceConstructionList", description = "공간공사")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class SpaceConstructionList {

        @Schema(name = "totalSpaceAmount", title = "총 공간공사 금액", type = "Integer", example = "10000000")
        @NotBlank
        private int totalSpaceAmount;

        private List<ConstrcutionProcess> process;
    }

    @Schema(title = "Material", description = "자재")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class ConstrcutionProcess {

        @Schema(name = "constructionField", title = "공사영역", required = true, example = "목공")
        @NotBlank
        private String constructionField;

        @Schema(name = "constructionFieldType", title = "공사영역타입", required = true, example = "CARPENTRY")
        @NotBlank
        private String constructionFieldType;

        @Schema(name = "totalAmount", title = "금액", required = true, example = "1500000")
        @NotBlank
        private int totalAmount;

        @Builder.Default
        private List<Material> material = new ArrayList<>();
    }

    @Schema(title = "Material", description = "자재")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class Material {
        @Schema(name = "materialName", title = "자재명", required = true, example = "목공상품명")
        private String materialName;
    }
}
