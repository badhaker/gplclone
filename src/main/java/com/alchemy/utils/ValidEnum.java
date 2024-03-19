package com.alchemy.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
	String message() default "Invalid value for enum * ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends Enum<?>> enumClass();

	boolean ignoreCase() default false;

}
