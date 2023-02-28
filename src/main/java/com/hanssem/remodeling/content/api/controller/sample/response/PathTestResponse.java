package com.hanssem.remodeling.content.api.controller.sample.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(title = "PathTestResponse", description = "도메인 처리 결과 데이터")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathTestResponse implements Serializable {

    @Schema(name = "pathData", title = "mapper 처리 데이터", type = "String")
    private String pathData;

}
