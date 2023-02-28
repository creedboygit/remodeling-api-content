package com.hanssem.remodeling.content.api.service.notification;


import com.hanssem.remodeling.content.api.repository.client.HanssemMallClient;
import com.hanssem.remodeling.content.api.repository.client.NotificationClient;
import com.hanssem.remodeling.content.api.service.notification.dto.NotificationPushReqDto;
import com.hanssem.remodeling.content.api.service.notification.dto.NotificationSendUserReqDto;
import com.hanssem.remodeling.content.api.service.notification.dto.NotificationTalkReqDto;
import com.hanssem.remodeling.content.api.service.notification.util.NotificationUtil;
import com.hanssem.remodeling.content.api.service.notification.vo.HsMallCustomerDetailVo;
import com.hanssem.remodeling.content.api.service.notification.vo.NotificationTalkVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.constant.NotificationTalkType;
import com.hanssem.remodeling.content.constant.NotificationType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import com.hanssem.remodeling.content.constant.YnType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final AuthClaim authClaim; // Header 정보가 담겨 있는 객체입니다.

    private final HanssemMallClient hanssemMallClient;
    private final NotificationClient notificationClient;


    public void sendNotificationTalk(NotificationTalkType notificationTalkType) {

        String custNoStr = Optional.ofNullable(authClaim.getUserId())
                .orElseThrow(() -> new CustomException(ResponseCode.BAD_REQUEST_TOKEN))
                .toString();

        this.checkCustInfo(custNoStr);

        NotificationTalkReqDto notificationTalkReqDto = this.getNotificationTalkRequestDto(notificationTalkType);

        NotificationSendUserReqDto notificationSendUserReqDto = NotificationSendUserReqDto.builder()
                .notificationType(List.of(NotificationType.TMS))
                .custNo(custNoStr)
                .ignoreOptInAgree(YnType.Y)
                .templateCode(notificationTalkReqDto.getTemplateCode())
                .tmsMessage(notificationTalkReqDto.getTmsMessage())
                .build();

        log.debug("# TALK notificationSendUserReqDto: {}", notificationSendUserReqDto);
        this.sendNotificationUser(notificationSendUserReqDto);
    }

    private NotificationTalkReqDto getNotificationTalkRequestDto(NotificationTalkType notificationTalkType) {

        NotificationTalkReqDto notificationTalkReqDto = new NotificationTalkReqDto();
        if (notificationTalkType == NotificationTalkType.SERVICE_CENTER) {
            notificationTalkReqDto.setTemplateCode(NotificationUtil.SERVICE_CENTER_TEMPLATE_CODE);

            String replacedMessage = NotificationUtil.SERVICE_CENTER_TMS_MESSAGE.replace(NotificationUtil.SERVICE_CENTER_URL_KEY, NotificationUtil.SERVICE_CENTER_URL_VALUE);
            notificationTalkReqDto.setTmsMessage(replacedMessage);
        }

        return notificationTalkReqDto;
    }

    private NotificationPushReqDto getNotificationPushRequestDto() {

        return new NotificationPushReqDto();
    }

    @Deprecated
//    notification은 send API 하나로 알림톡과 푸시를 모두 처리하나, content에서는 분리해서 처리.
    public void sendNotificationPush() {
        NotificationPushReqDto notificationPushReqDto = getNotificationPushRequestDto();

        NotificationSendUserReqDto notificationSendUserReqDto = NotificationSendUserReqDto.builder()
                .notificationType(List.of(NotificationType.APP))
                .custNo("")
                .title(notificationPushReqDto.getTitle())
                .appMessage(notificationPushReqDto.getAppMessage())
                .webUrl(notificationPushReqDto.getWebUrl())
                .imageUrl(notificationPushReqDto.getImageUrl())
                .appLinkChannel(notificationPushReqDto.getAppLinkChannel())
                .appLinkTarget(notificationPushReqDto.getAppLinkTarget())
                .appLinkVal(notificationPushReqDto.getAppLinkVal())
                .ignoreOptInAgree(YnType.Y)
                .build();

        log.debug("# PUSH notificationSendUserReqDto: {}", notificationSendUserReqDto);
        this.sendNotificationUser(notificationSendUserReqDto);
    }

    private void sendNotificationUser(NotificationSendUserReqDto notificationSendUserReqDto) {
        NotificationTalkVo notificationTalkVo = notificationClient.sendNotificationUser(notificationSendUserReqDto);
        log.debug("# notificationTalkVo: {}", notificationTalkVo);

        if (notificationTalkVo.getCode() != HttpStatus.OK.value()
                || !notificationTalkVo.getReturnCode().equals(NotificationUtil.RETURN_CODE_OK)) {

            ResponseCode errorCode = ResponseCode.ERROR_NOTIFICATION_SEND;
            log.error("# 알림톡에러-notification서비스: responseCode: {}, message: {}, data: {}",
                    notificationTalkVo.getCode(), notificationTalkVo.getMessage(), notificationTalkVo.getData());

            throw new CustomException(errorCode);
        }
    }

    private void checkCustInfo(String custNoStr) {

        HsMallCustomerDetailVo hsMallCustomerDetailVo = hanssemMallClient.findCustomerDetail(custNoStr);

        int code = Integer.parseInt(hsMallCustomerDetailVo.getCode());

        if (code == HttpStatus.OK.value()) {
            if (hsMallCustomerDetailVo.getMobileNo().equals(NotificationUtil.DEFAULT_MOBILE_NO)) {

                ResponseCode responseCode = ResponseCode.INVALID_MOBILE_NO;
                log.error("# 알림톡에러-사용자정보조회: responseCode: {}, errorCode: {}, message: {}, custNo: {}, mobileNo: {}",
                        code, responseCode, responseCode.getMessage(), hsMallCustomerDetailVo.getCustNo(), hsMallCustomerDetailVo.getMobileNo());

                throw new CustomException(responseCode);
            }

        } else {
            if (code == ResponseCode.NOT_FOUND.getCode()) {
                ResponseCode responseCode = ResponseCode.INVALID_CUST_NO;
                log.error("# 알림톡에러-사용자정보조회: responseCode: {}, errorCode: {}, message: {}, custNo: {}",
                        code, responseCode, responseCode.getMessage(), hsMallCustomerDetailVo.getCustNo());
                throw new CustomException(responseCode);
            }

            log.error("# 알림톡에러-사용자정보조회: responseCode: {}", code);
            throw new CustomException(ResponseCode.ERROR_FEIGN_CLIENT);
        }
    }
}
