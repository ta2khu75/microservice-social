package dev.ta2khu74.identity.exception;

import org.springframework.http.HttpStatus;
public class UnAuthorizedException extends AbstractException{
	public UnAuthorizedException(String message) {
        super(message, 403, HttpStatus.FORBIDDEN);
    }
}
