package dev.ta2khu74.identity.exception;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {
	private int messageCode;
	private HttpStatusCode statusCode;
	public AbstractException(String message, int messageCode, HttpStatusCode statusCode) {
		super(message);
		this.messageCode = messageCode;
		this.statusCode = statusCode;
	}
}
