package com.hanssem.remodeling.content.common.validator;


import com.hanssem.remodeling.content.api.service.notification.dto.NotificationSendUserReqDto;
import com.hanssem.remodeling.content.constant.MatchType;
import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ConditionalMatchFieldValidator implements ConstraintValidator<ConditionalMatchField, NotificationSendUserReqDto> {

    private String message;
    private String first;
    private String second;
    private MatchType matchType;

    @Override
    public void initialize(ConditionalMatchField constraintAnnotation) {
        message = constraintAnnotation.message();
        first = constraintAnnotation.first();
        second = constraintAnnotation.second();
        matchType = constraintAnnotation.matchType();
    }

    @Override
    public boolean isValid(NotificationSendUserReqDto sendUserRequest, ConstraintValidatorContext context) {
        try {
            boolean isValid;

            Field firstField = sendUserRequest.getClass().getDeclaredField(first);
            firstField.setAccessible(true);
            String firstVal = (String) firstField.get(sendUserRequest);
            Field secondField = sendUserRequest.getClass().getDeclaredField(second);
            secondField.setAccessible(true);
            String secondVal = (String) secondField.get(sendUserRequest);

            isValid = switch (matchType) {
                case EQ -> firstVal.equals(secondVal);
                case NO_EQ -> !firstVal.equals(secondVal);
                case NOT_EMPTY_LEAST_ONE -> StringUtils.isEmpty(firstVal) && StringUtils.isEmpty(secondVal);
            };

            if (isValid) {
                ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
                context.disableDefaultConstraintViolation();
                violationBuilder.addPropertyNode(first).addConstraintViolation();
                return false;
            }
            return true;
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}
