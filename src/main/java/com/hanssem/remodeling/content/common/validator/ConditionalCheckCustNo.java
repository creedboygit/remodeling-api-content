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
@Constraint(validatedBy = ConditionalCheckCustNoValidator.class)
@Documented
public @interface ConditionalCheckCustNo {

    String message() default "Not empty custNo.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
