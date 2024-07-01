package dev.ta2khu74.identity.exception;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.Getter;
@Getter
public class InvalidException extends MethodArgumentNotValidException {
	private final short errorCode = 400;

	public InvalidException(BindingResult bindingResult) {
		super(null, bindingResult);
	}
}
