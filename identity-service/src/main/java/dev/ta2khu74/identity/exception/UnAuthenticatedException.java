package dev.ta2khu74.identity.exception;

import org.springframework.http.HttpStatus;

public class UnAuthenticatedException extends AbstractException{
	public UnAuthenticatedException(String message) {
		super(message, 401, HttpStatus.UNAUTHORIZED);
	}
}
