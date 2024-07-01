package dev.ta2khu74.identity.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.ta2khu74.identity.dto.response.ApiResponse;

@ControllerAdvice
public class AdviceException {
	@ExceptionHandler(Exception.class)
	ResponseEntity<ApiResponse<?>> handleNotFoundException(Exception e) {
		return ResponseEntity.badRequest().body(ApiResponse.builder().code(555).message(e.getMessage()).build());
	}

	@ExceptionHandler(AbstractException.class)
	ResponseEntity<ApiResponse<?>> handleAppException(AbstractException e) {
		return ResponseEntity.status(e.getStatusCode())
				.body(ApiResponse.builder().code(e.getMessageCode()).message(e.getMessage()).build());
	}

	@ExceptionHandler(InvalidException.class)
	ResponseEntity<ApiResponse<?>> handleInvalidException(InvalidException e) {
		return ResponseEntity.badRequest().body(
				ApiResponse.builder().code(e.getErrorCode()).message(e.getFieldError().getDefaultMessage()).build());
	}
	@ExceptionHandler(AccessDeniedException.class)
	ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
		try {
			throw new UnAuthorizedException(ex.getMessage());
		}catch (UnAuthorizedException e) {
        return ResponseEntity.status(e.getStatusCode()).body(
                ApiResponse.builder().code(e.getMessageCode()).message(e.getMessage()).build());
		}
    }
}
