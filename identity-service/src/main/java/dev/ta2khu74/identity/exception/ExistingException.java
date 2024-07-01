package dev.ta2khu74.identity.exception;

import org.springframework.http.HttpStatus;

public class ExistingException extends AbstractException {
	public ExistingException(String message) {
		super(message, 400, HttpStatus.BAD_REQUEST);
	}
}
