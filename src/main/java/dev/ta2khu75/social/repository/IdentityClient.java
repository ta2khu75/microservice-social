package dev.ta2khu75.social.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import dev.ta2khu75.social.dto.req.IntrospectRequest;
import dev.ta2khu75.social.dto.res.ApiResponse;
import reactor.core.publisher.Mono;

public interface IdentityClient {
	@PostExchange(url="/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
	Mono<ApiResponse<Boolean>> introspect(@RequestBody IntrospectRequest introspectRequest);
}
