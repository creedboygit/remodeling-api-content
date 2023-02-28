package com.hanssem.remodeling.content.common.validator;

import com.hanssem.remodeling.content.api.service.notification.dto.NotificationSendUserReqDto;
import com.hanssem.remodeling.content.constant.NotificationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NotEmptyMessageValidator implements ConstraintValidator<NotEmptyMessage, NotificationSendUserReqDto> {

    private String message;

    @Override
    public void initialize(NotEmptyMessage constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(NotificationSendUserReqDto sendUserRequest, ConstraintValidatorContext context) {
        try {
            boolean isAppMessageValid = sendUserRequest.getNotificationType().contains(NotificationType.APP) && StringUtils.isEmpty(sendUserRequest.getAppMessage());
            if (isAppMessageValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(this.message).addPropertyNode("appMessage").addConstraintViolation();
                return false;
            }
            boolean isTmsMessageValid = sendUserRequest.getNotificationType().contains(NotificationType.TMS) && StringUtils.isEmpty(sendUserRequest.getTmsMessage());
            if (isTmsMessageValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(this.message).addPropertyNode("tmsMessage").addConstraintViolation();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
