package dev.ta2khu74.identity.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractException {
	public NotFoundException(String message) {
		super(message, 404, HttpStatus.NOT_FOUND);
	}
}
