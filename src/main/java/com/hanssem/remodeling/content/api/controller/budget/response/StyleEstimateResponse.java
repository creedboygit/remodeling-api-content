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

@Schema(title = "StyleEstimateResponse", description = "담은 공간 견적")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class StyleEstimateResponse {

    @Builder.Default
    private List<StyleEstimateResponse.SpaceImage> spaceImagesList = new ArrayList<>();
    //공사영역
    @Schema(name = "totalConstructionAmount", title = "총 견적금액", type = "Integer", example = "14500000")
    @NotBlank
    private int totalConstructionAmount;

    private StyleEstimateResponse.CommonConstruction commonConstruction;

    private StyleEstimateResponse.SpaceConstruction spaceConstruction;


    @Schema(title = "SpaceImage", description = "공간이미지 영역")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class SpaceImage {

        @Schema(name = "spaceName", title = "공간명", type = "String", example = "공용욕실")
        @NotBlank
        private String spaceName;

        @Schema(name = "styleTitle", title = "스타일 타이틀", type = "String", example = "2인 가구를 위한 공용욕실")
        @NotBlank
        private String styleTitle;

        @Schema(name = "styleMainImageUrl", title = "스타일메인이미지URL", type = "String", example = "https://dev-static.remodeling.hanssem.com/content/budget/style/main.jpg")
        @NotBlank
        private String styleMainImageUrl;

        @Schema(name = "styleSubImageUrl", title = "스타일서브이미지URL", type = "String", example = "https://dev-static.remodeling.hanssem.com/content/budget/style/sub.jpg")
        @NotBlank
        private String styleSubImageUrl;

        public String getStyleMainImageUrl() {
            return GlobalConstants.makeCdnUrl(styleMainImageUrl);
        }

        public String getStyleSubImageUrl() {
            return GlobalConstants.makeCdnUrl(styleSubImageUrl);
        }
    }

    @Schema(title = "CommonConstruction", description = "공통공사영역(기본공통 + 공통공사)")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class CommonConstruction {
        //공통공사 영역
        @Schema(name = "totalCommonConstructionAmount", title = "총 공통공사금액", type = "Integer", example = "1500000")
        @NotBlank
        private int totalCommonConstructionAmount;

        private List<StyleEstimateResponse.CommonConstructionProcess> process;
    }

    @Schema(title = "SpaceConstruction", description = "공간공사영역")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class SpaceConstruction {

        //공간공사 영역
        @Schema(name = "totalSpaceConstructionAmount", title = "총 공간공사금액", type = "Integer", example = "13000000")
        @NotBlank
        private int totalSpaceConstructionAmount;

        private List<StyleEstimateResponse.SpaceConstructionProcess> process;
    }

    @Schema(title = "CommonConstructionProcess", description = "공통공사공정")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class CommonConstructionProcess {

        @Schema(name = "constructionField", title = "공사영역", required = true, example = "목공")
        @NotBlank
        private String constructionField;

        @Schema(name = "constructionFieldType", title = "공사영역타입", required = true, example = "CARPENTRY")
        @NotBlank
        private String constructionFieldType;

        @Schema(name = "constructionAmount", title = "공사금액", required = true, example = "10000000")
        @NotBlank
        private int constructionAmount;

        @Builder.Default
        private List<StyleEstimateResponse.CommonMaterial> material = new ArrayList<>();
    }

    @Schema(title = "SpaceConstructionProcess", description = "공간공사공정")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class SpaceConstructionProcess {

        @Schema(name = "spaceName", title = "공간명", required = true, example = "자녀방")
        private String spaceName;

        @Schema(name = "spaceType", title = "공간코드", required = true, example = "CHILD_ROOM")
        private String spaceType;

        @Schema(name = "constructionAmount", title = "공사금액", required = true, example = "10000000")
        private int constructionAmount;

        @Builder.Default
        private List<StyleEstimateResponse.SpaceField> fields = new ArrayList<>();

    }

    @Schema(title = "SpaceField", description = "공간공사 공사영역")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class SpaceField {

        @Schema(name = "constructionField", title = "공사영역", required = true, example = "벽자재")
        private String constructionField;

        @Schema(name = "constructionFieldType", title = "공사영역타입", required = true, example = "WALL_MATERIAL")
        private String constructionFieldType;

        @Builder.Default
        private List<StyleEstimateResponse.SpaceMaterial> material = new ArrayList<>();
    }

    @Schema(title = "CommonMaterial", description = "공통공사 자재영역")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class CommonMaterial {

        @Schema(name = "materialName", title = "자재명", required = true, example = "목공상품명")
        private String materialName;
    }

    @Schema(title = "SpaceMaterial", description = "공간공사 자재영역")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class SpaceMaterial {

        @Schema(name = "materialName", title = "자재명", required = true, example = "한샘 벽자재")
        private String materialName;
    }
}
