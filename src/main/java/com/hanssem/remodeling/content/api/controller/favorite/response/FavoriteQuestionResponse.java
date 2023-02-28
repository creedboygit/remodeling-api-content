package com.hanssem.remodeling.content.api.controller.favorite.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = "FavoriteQuestionResponse", description = "인테리어취향테스트 문항 조회 객체")
public class FavoriteQuestionResponse implements Serializable {
    private List<FavoriteTagResponse> colors = new ArrayList<>();
    private List<FavoriteTagResponse> keywords = new ArrayList<>();
    private List<FavoriteTagResponse> materials = new ArrayList<>();
    private List<FavoriteImageResponse> images = new ArrayList<>();
}
