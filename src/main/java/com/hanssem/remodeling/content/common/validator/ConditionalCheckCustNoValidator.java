package com.hanssem.remodeling.content.common.validator;

import com.hanssem.remodeling.content.api.service.notification.dto.NotificationSendUserReqDto;
import com.hanssem.remodeling.content.constant.NotificationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ConditionalCheckCustNoValidator implements ConstraintValidator<ConditionalCheckCustNo, NotificationSendUserReqDto> {

    private String message;

    @Override
    public void initialize(ConditionalCheckCustNo constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(NotificationSendUserReqDto sendUserRequest, ConstraintValidatorContext context) {
        boolean isValid;
        isValid = sendUserRequest.getNotificationType() != null && sendUserRequest.getNotificationType().contains(NotificationType.APP) && StringUtils.isEmpty(sendUserRequest.getCustNo());
        if (isValid) {
            ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
            context.disableDefaultConstraintViolation();
            violationBuilder.addPropertyNode("custNo").addConstraintViolation();
            return false;
        }
        return true;
    }
}
