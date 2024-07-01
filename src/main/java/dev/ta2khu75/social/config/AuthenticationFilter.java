package dev.ta2khu75.social.config;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.ta2khu75.social.dto.res.ApiResponse;
import dev.ta2khu75.social.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered{
	IdentityService identityService;
	ObjectMapper objectMapper;
	@Override
	public int getOrder() {
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Authentication filter ....");
		//get token
		List<String> authHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
		if(CollectionUtils.isEmpty(authHeader)) {
			return unauthenticated(exchange.getResponse());
		}
		String token= authHeader.get(0).replace("Bearer", "");
		log.info("token ",token);
		return identityService.introspec(token).flatMap(t -> {
			if(t.getData()) {
				return chain.filter(exchange);
			}return unauthenticated(exchange.getResponse()); 
		}).onErrorResume(t -> unauthenticated(exchange.getResponse()));
				//.subscribe(t -> log.info(t.getData()+""));
//		return chain.filter(exchange);
	}
	private Mono<Void> unauthenticated(ServerHttpResponse response){
		ApiResponse<Void> apiResponse=new ApiResponse(404, "Unauthenticated", null);
		String body=null;
		try {
			body=objectMapper.writeValueAsString(apiResponse);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
	}

}
