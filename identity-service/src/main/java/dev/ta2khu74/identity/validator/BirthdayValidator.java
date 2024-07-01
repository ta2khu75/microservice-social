package dev.ta2khu74.identity.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BirthdayValidator implements ConstraintValidator<BirthdayConstraint, LocalDate> {
	private int min;

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (Objects.isNull(value))
			return true;
		long year = ChronoUnit.YEARS.between(value, LocalDate.now());
		return year >= min;
	}

	@Override
	public void initialize(BirthdayConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		min = constraintAnnotation.min();
	}

}
