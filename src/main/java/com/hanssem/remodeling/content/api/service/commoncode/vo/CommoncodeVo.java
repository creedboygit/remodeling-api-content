package com.hanssem.remodeling.content.api.service.commoncode.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanssem.remodeling.content.api.service.category.vo.DotcomCategoryListVo.DotcomCategoryListApiResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommoncodeVo {

    private String code;
    private String message;
    private List<CommoncodeResponse> data = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class CommoncodeResponse {

        @JsonProperty("codeId")
        private String codeId;

        @JsonProperty("codeValueId")
        private String codeValueId;

        @JsonProperty("codeValue")
        private String codeValue;

        @JsonProperty("codeValueName")
        private String codeValueName;

        @JsonProperty("codeValueSeq")
        private int codeValueSeq;

        @JsonProperty("description")
        private String description;

        @JsonProperty("validEndDatetime")
//        private LocalDateTime validEndDatetime;
        private String validEndDatetime;

        @JsonProperty("etcValue1")
        private String etcValue1;

        @JsonProperty("etcValue2")
        private String etcValue2;
    }
}
