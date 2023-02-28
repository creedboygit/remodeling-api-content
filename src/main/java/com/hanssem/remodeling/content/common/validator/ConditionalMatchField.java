package com.hanssem.remodeling.content.common.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.hanssem.remodeling.content.constant.MatchType;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ConditionalMatchFieldValidator.class)
@Documented
public @interface ConditionalMatchField {

    String message() default "{constraints.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*
     * 의존성을 체크할 필드 #1
     */
    String first();

    /*
     * 의존성을 체크할 필드 #2
     */
    String second();

    /*
     * 조건
     */
    MatchType matchType();


    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        ConditionalMatchField[] value();
    }
}
