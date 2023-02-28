package com.hanssem.remodeling.content.api.service.notification.dto;

import com.hanssem.remodeling.content.common.validator.ConditionalCheckCustNo;
import com.hanssem.remodeling.content.common.validator.ConditionalMatchField;
import com.hanssem.remodeling.content.common.validator.NotEmptyMessage;
import com.hanssem.remodeling.content.common.validator.NotEmptyTemplateCode;
import com.hanssem.remodeling.content.constant.MatchType;
import com.hanssem.remodeling.content.constant.NotificationType;
import com.hanssem.remodeling.content.constant.YnType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Schema(name = "알림 발송 파라미터", description = "알림 발송 파라미터")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@NotEmptyMessage
@NotEmptyTemplateCode
@ConditionalCheckCustNo
@ConditionalMatchField.List({
        @ConditionalMatchField(first = "custNo", second = "phoneNo", matchType = MatchType.NOT_EMPTY_LEAST_ONE, message = "[custNo, phoneNo] Not Empty at least one."),
})
public final class NotificationSendUserReqDto {

    // 알림 종류(복수개 가능하며 순서에 따라 발송 시도)
    @NotNull
    @Schema(title = "notificationType", description = "[공통] notificationType: 알림 종류(APP, TMS)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<NotificationType> notificationType;

    // 수신 대상
    @Schema(title = "custNo", description = "[공통] custNo: 수신대상 custNo, custNo 혹은 phoneNo 둘 중 하나는 필수 (앱 : 한샘몰ID, 알림톡&SMS : 폰번호로 치환 되어 발송)", type = "string", example = "4871749", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String custNo;

    // 알림동의와 관계없이 발송할 것인지 여부(default: Y = 알림동의 여부와 관계없이 발송함)
    @Builder.Default
    @Schema(title = "ignoreOptInAgree", description = "[공통] ignoreOptInAgree: 알림수신 동의여부와 관계없이 발송할 것인지?(Y|N, default: Y = 알림동의 여부와 관계없이 발송함)", defaultValue = "Y", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private YnType ignoreOptInAgree = YnType.N;

    @Builder.Default
    @Schema(title = "sendAllType", description = "[공통] sendAllType: 푸시알림, 알림톡 모두 발송(default: N)", defaultValue = "N", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private YnType sendAllType = YnType.N;

    @Schema(title = "phoneNo", description = "[공통] phoneNo: 수신대상 전화번호(custNo 혹은 phoneNo 둘 중 하나는 필수)", type = "string", example = "01000000000", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phoneNo;


    @Schema(title = "templateCode", description = "[알림톡] templateCode: 카카오 알림톡 발송 템플릿 코드(TMS 발송시 필수)", type = "string", example = "AT_20221214152207", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String templateCode;

    @Schema(title = "tmsMessage", description = "[알림톡] tmsMessage: 카카오 알림톡 메세지내용(TMS 발송시 필수)", type = "string", example = "고객님, 한샘 입니다!\n고객님께서 요청하신 \"언택트 3D 제안서 \"가 도착했습니다.\n\n담당 디자이너가 직접 작업한 3D디자인과 견적서를 확인 해 보세요.\n※ 제안서의 확인은 \"한샘App\"을 통해서만 가능 합니다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String tmsMessage;

    @Schema(title = "title", description = "[푸시] title: 제목(APP Only)", type = "string", example = "[제목]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;

    @Schema(title = "appMessage", description = "[푸시] appMessage: 앱푸시 메세지내용(APP 발송시 필수)", type = "string", example = "고객님, 접수가 완료 되었습니다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String appMessage;

    @URL
    @Schema(title = "webUrl", description = "[푸시] webUrl: 푸시알림 link url", type = "string", example = "https://remodeling.hanssem.com/abc/def", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String webUrl;

    @URL
    @Schema(title = "imageUrl", description = "[푸시] imageUrl: 알림 이미지 url", type = "string", example = "https://static.remodeling.hanssem.com/image/image1.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String imageUrl;

    @Schema(title = "appLinkChannel", description = "[푸시] appLinkChannel", type = "string", example = "REMODELING", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String appLinkChannel;

    @Schema(title = "appLinkTarget", description = "[푸시] appLinkTarget", type = "string", example = "REMODELING_MANAGER", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String appLinkTarget;

    @Schema(title = "appLinkVal", description = "[푸시] appLinkVal", type = "string", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String appLinkVal;

}
