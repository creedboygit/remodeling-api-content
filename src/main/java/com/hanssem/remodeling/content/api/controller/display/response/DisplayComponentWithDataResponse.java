package com.hanssem.remodeling.content.api.controller.display.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Schema(title = "DisplayComponentWithDataResponse", description = "DisplayComponentWithDataResponse")
@Getter
@Setter
@ToString(callSuper = true)
//@JsonIgnoreProperties(ignoreUnknown = true)
// Object는 LinkedHashMap으로 변환되어 역직렬화시 기존필드가 사라진 경우 이슈가 있으므로, @JsonIgnoreProperties 필요.
// redis의 objectMapper에 FAIL_ON_UNKNOWN_PROPERTIES 옵션 추가와 동일한 목적.
// redis에서 Object로 직렬화 시, json데이터에 type정보를 포함하고 있어야 한다. JsonTypeInfo.Id.CLASS 등.
// 참고: https://www.baeldung.com/jackson-linkedhashmap-cannot-be-cast
public class DisplayComponentWithDataResponse extends DisplayComponentResponse {

    @Schema(title = "componentData", description = "componentLayoutCd에 따라 포맷이 다름. '전시 컴포넌트 데이터 조회' 의 결과를 할당함.",
            example = "componentLayoutCd에 따라 포맷이 다름. '전시 컴포넌트 데이터 조회' 의 결과를 할당함.", type = "object")
    private Object componentData;

}
