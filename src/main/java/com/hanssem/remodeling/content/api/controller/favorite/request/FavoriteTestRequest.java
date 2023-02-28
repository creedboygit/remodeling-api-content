package com.hanssem.remodeling.content.api.controller.favorite.request;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.constant.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "FavoriteTestRequest", description = "인테리어취향테스트 답변 객체")
@Getter
@Setter
@ToString
public class FavoriteTestRequest {

    @Schema(title = "images", description = "image id", type = "List<Long>", example = "[1,2,3]", required = true)
    private List<Long> images = new ArrayList<>();

    @Schema(title = "colors", description = "color id", type = "List<Long>", example = "[1,2]", required = true)
    private List<Long> colors = new ArrayList<>();

    @Schema(title = "keywords", description = "keyword id", type = "List<Long>", example = "[1,2,3]", required = true)
    private List<Long> keywords = new ArrayList<>();

    @Schema(title = "materials", description = "material id", type = "List<Long>", example = "[1,2]", required = true)
    private List<Long> materials = new ArrayList<>();

    public void validated() {
        if (images == null || images.size() != 3)
            throw new CustomException(ResponseCode.FAVORITE_INVALID_PARAMS_IMAGES);
        else if (colors == null || colors.size() != 2)
            throw new CustomException(ResponseCode.FAVORITE_INVALID_PARAMS_COLORS);
        else if (keywords == null || keywords.size() != 3)
            throw new CustomException(ResponseCode.FAVORITE_INVALID_PARAMS_KEYWORDS);
        else if (materials == null || materials.size() != 2)
            throw new CustomException(ResponseCode.FAVORITE_INVALID_PARAMS_MATERIALS);
    }

}
