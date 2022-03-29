package com.project.sample.api.demo.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * 구분된 파라메터 중 허용된 값들을 체크한다.
 */
public class AllowSplitParamsValidator implements ConstraintValidator<AllowSplitParams, String> {

	private String[] params;

	@Override
	public void initialize(AllowSplitParams constraintAnnotation) {
		this.params = constraintAnnotation.values();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String[] splitStrs = value.split("\\|");
		for (String param : params) {
			if (!Arrays.stream(splitStrs).anyMatch(item -> param.equals(item))) {
				return true;
			}
		}
		return false;
	}
}
