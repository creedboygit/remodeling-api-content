package com.hanssem.remodeling.content.api.controller.event.request;

import com.hanssem.remodeling.content.constant.EventStatusType;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "EventSearchDetailRequest", description = "이벤트 상세 조회")
@Getter
@Setter
@ToString
@ParameterObject
public class EventSearchDetailRequest {

    @NotNull(message = "eventStatusType 은(는) 필수 입니다.")
    @Schema(title = "eventStatusType", description = "상태: 진행/종료/담첨안내", type = "string", example = "ING", implementation = EventStatusType.class, requiredMode = RequiredMode.REQUIRED)
    private EventStatusType eventStatusType;

    @Hidden // AuthClaim.userId 를 사용함.
    @Schema(title = "custNo", description = "custNo", type = "string", example = "")
    private String custNo;
}
