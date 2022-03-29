package com.project.sample.api.demo.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Check allow paramter validator.
 * 허용파라메터를 체크한다.
 */
public class AllowParamsValidator implements ConstraintValidator<com.project.sample.api.demo.validate.AllowParams, String> {

	private String[] params;

	@Override
	public void initialize(com.project.sample.api.demo.validate.AllowParams constraintAnnotation) {
		this.params = constraintAnnotation.values();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		for (String param : params) {
			if (value != null && value.equals(param)) {
				return true;
			}
		}
		return false;
	}
}
