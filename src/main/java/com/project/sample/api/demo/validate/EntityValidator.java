package com.project.sample.api.demo.validate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Iterator;
import java.util.Set;

/**
 * Entity Validator
 */
@Slf4j
public class EntityValidator {

	/**
	 * JSR-303 Validate 을 이용하여 Object 를 검증한다.
	 * 검증이 "실패"할 경우 true 를 리턴하며<
	 * "성공"일 경우 false 를 돌려준다.
	 *
	 * @Mehtod Name : isValid
	 * @param object : 검증대상 Object
	 * @param classes : 검증대상 Group, 없을 경우 Default.class
	 * @return
	 */
	public static <T> boolean isValid(T object, Class<?> ... classes) {

		if (classes == null || classes.length == 0 || classes[0] == null) {
			classes = new Class<?>[] { Default.class};
		}

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, classes);
		if (constraintViolations.size() > 0) {

			StringBuilder sb = new StringBuilder();
			sb.append(String.format("\r\n======== %s Invalid Error ========\r\n", object.getClass().getSimpleName()));

			Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
			while (it.hasNext()) {
				ConstraintViolation<T> error = it.next();
				sb.append("field : ").append(error.getPropertyPath()).append("\r\n")
				.append("message : ").append(error.getMessage()).append("\r\n")
				.append("invalid value : [").append(error.getInvalidValue()).append("]\r\n");
				sb.append("----------------------------------------\r\n");
			}
			log.warn(sb.toString());

			return true;
		}

		return false;
	}
}
