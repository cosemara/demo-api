package com.project.sample.api.demo.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 구분된 문자열 중 허용된 파라메터를 체크한다.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = com.project.sample.api.demo.validate.AllowSplitParamsValidator.class)
@Documented
public @interface AllowSplitParams {

	String message() default "The parameter values are not allowed.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] values();
}
