package com.hanssem.remodeling.content.common.validator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyTemplateCodeValidator.class)
@Documented
public @interface NotEmptyTemplateCode {

    String message() default "알림톡 발송시에는 templateCode가 필수 입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
