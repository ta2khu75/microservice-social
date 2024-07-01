package dev.ta2khu74.identity.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ta2khu74.identity.dto.response.ApiResponse;
import dev.ta2khu74.identity.exception.UnAuthenticatedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		try {
			throw new UnAuthenticatedException("Authentication");
		} catch (UnAuthenticatedException e) {
			response.setStatus(e.getStatusCode().value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ApiResponse<?> apiResponse=ApiResponse.builder().code(e.getMessageCode()).message(e.getMessage()).build();
			ObjectMapper objectMapper=new ObjectMapper();
			response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
			response.flushBuffer();
		}
	}

}
