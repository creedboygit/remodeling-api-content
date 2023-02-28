package com.hanssem.remodeling.content.api.controller.event.request;

import com.hanssem.remodeling.content.common.model.HsMallPageRequest;
import com.hanssem.remodeling.content.constant.EventChannelType;
import com.hanssem.remodeling.content.constant.EventMallType;
import com.hanssem.remodeling.content.constant.EventStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "EventSearchRequest", description = "이벤트 목록 조회")
@Getter
@Setter
@ToString
@ParameterObject
public class EventSearchRequest extends HsMallPageRequest {

    @NotNull(message = "eventMallType 은(는) 필수 입니다.")
    @Schema(title = "eventMallType", description = "MallType: REMODELING/DOTCOM", type = "enum", implementation = EventMallType.class, requiredMode = RequiredMode.NOT_REQUIRED, hidden = true)
    private final EventMallType eventMallType = EventMallType.REMODELING;

    @NotNull(message = "eventChannelType 은(는) 필수 입니다.")
    @Schema(title = "eventChannelType", description = "채널: PC/MOBILE", type = "string", example = "MOBILE", implementation = EventChannelType.class, requiredMode = RequiredMode.REQUIRED)
    private EventChannelType eventChannelType;

    @NotNull(message = "eventStatusType 은(는) 필수 입니다.")
    @Schema(title = "eventStatusType", description = "상태: 진행/종료/담첨안내", type = "string", example = "ING", implementation = EventStatusType.class, requiredMode = RequiredMode.REQUIRED)
    private EventStatusType eventStatusType;

    protected EventSearchRequest(Integer page, Integer size) {
        super(page, size);
    }
}
