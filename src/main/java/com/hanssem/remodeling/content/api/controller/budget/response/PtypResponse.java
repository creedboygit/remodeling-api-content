package com.hanssem.remodeling.content.api.controller.budget.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Schema(title = "PtypResponse", description = "평형대별 방, 욕실 선택 가능 옵션 정보")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
public class PtypResponse implements Serializable {

    @Schema(name = "ptypData", title = "평형대, 욕실, 방 개수 정보", required = true)
    private List<PtypData> ptypData;

    /* 평형대별로 선택 가능한 방,욕실 데이터 */
    @Schema(title = "PtypData", description = "평형대별로 선택 가능한 방,욕실 개수 데이터")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class PtypData {

        @Schema(name = "ptypMId", title = "평형대ID", type = "Long", example = "1")
        @NotBlank
        private Long ptypMId;

        @Schema(name = "ptypValue", title = "평형대", type = "Integer", example = "20")
        @NotBlank
        private int ptypValue;

        @Schema(name = "room", title = "방", required = true)
        @NotBlank
        private List<PtypRoomOption> room;

        @Schema(name = "bath", title = "욕실", required = true)
        @NotBlank
        private List<PtypBathOption> bath;
    }

    /* 선택가능한 방 개수 */
    @Schema(title = "PtypRoomOption", description = "선택가능한 방 개수 리스트")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class PtypRoomOption {

        @Schema(name = "optionId", title = "평형대옵션아이디", type = "Long", example = "1")
        @NotBlank
        private Long optionId;

        @Schema(name = "count", title = "방(개수)", type = "Integer", example = "2")
        @NotBlank
        private int count;
    }

    /* 선택 가능한 욕실 개수 */
    @Schema(title = "PtypBathOption", description = "선택가능한 욕실 개수 리스트")
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @Builder
    public static class PtypBathOption {

        @Schema(name = "optionId", title = "평형대옵션아이디", type = "Long", example = "1")
        @NotBlank
        private Long optionId;

        @Schema(name = "count", title = "욕실(개수)", type = "Integer", example = "2")
        @NotBlank
        private int count;
    }
}
