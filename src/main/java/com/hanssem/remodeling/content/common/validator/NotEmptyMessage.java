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
@Constraint(validatedBy = NotEmptyMessageValidator.class)
@Documented
public @interface NotEmptyMessage {

    String message() default "필드가 비어 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
