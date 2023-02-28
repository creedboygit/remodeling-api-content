package com.hanssem.remodeling.content.common.validator;

import com.hanssem.remodeling.content.api.service.notification.dto.NotificationSendUserReqDto;
import com.hanssem.remodeling.content.constant.NotificationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NotEmptyTemplateCodeValidator implements ConstraintValidator<NotEmptyTemplateCode, NotificationSendUserReqDto> {

    private String message;

    @Override
    public void initialize(NotEmptyTemplateCode constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(NotificationSendUserReqDto request, ConstraintValidatorContext context) {
        try {
            boolean isValid = request.getNotificationType().contains(NotificationType.TMS) && StringUtils.isEmpty(request.getTemplateCode());
            if (isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(this.message).addPropertyNode("templateCode").addConstraintViolation();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
