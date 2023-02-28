package com.hanssem.remodeling.content.admin.controller.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.File;
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
@Schema(title = "CategoryResponse", description = "카테고리 정보 응답객체")
public class ExcelResponse {

    @Schema(title = "rows", description = "라인수", example = "100")
    private int rows;

    @Schema(title = "file", description = "파일")
    private File file;

}
