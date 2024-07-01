package dev.ta2khu74.identity.controller;

import java.text.ParseException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import org.springframework.web.bind.annotation.RequestBody;
import dev.ta2khu74.identity.dto.request.AuthenticationRequest;
import dev.ta2khu74.identity.dto.request.IntrospectRequest;
import dev.ta2khu74.identity.dto.request.LogoutRequest;
import dev.ta2khu74.identity.dto.request.RefreshRequest;
import dev.ta2khu74.identity.dto.response.ApiResponse;
import dev.ta2khu74.identity.dto.response.AuthenticationResponse;
import dev.ta2khu74.identity.exception.InvalidException;
import dev.ta2khu74.identity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
	private final AuthenticationService service;

	@PostMapping("login")
	public ApiResponse<AuthenticationResponse> authenticate(
			@Validated @RequestBody AuthenticationRequest authentication, BindingResult bindingResult)
			throws InvalidException {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(bindingResult);
		}
		return ApiResponse.<AuthenticationResponse>builder().data(service.authenticate(authentication)).build();
	}

	@PostMapping("introspect")
	public ApiResponse<Boolean> instropect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
		return ApiResponse.<Boolean>builder().data(service.introspect(request)).build();
	}
	@PostMapping("refresh")
	public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) throws JOSEException, ParseException {
		return ApiResponse.<AuthenticationResponse>builder().data(service.refreshToken(request)).build();
	}
	@PostMapping("logout")
	public ApiResponse<Void> logout(@RequestBody LogoutRequest 	request) throws JOSEException, ParseException {
		service.logout(request);
		return ApiResponse.<Void>builder().build();
	}

}
