package dev.ta2khu75.social.service;

import org.springframework.stereotype.Service;

import dev.ta2khu75.social.dto.req.IntrospectRequest;
import dev.ta2khu75.social.dto.res.ApiResponse;
import dev.ta2khu75.social.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class IdentityService {
	final IdentityClient identityClient;
	public Mono<ApiResponse<Boolean>> introspec(String token) {
		return identityClient.introspect(new IntrospectRequest(token));
	}
}
