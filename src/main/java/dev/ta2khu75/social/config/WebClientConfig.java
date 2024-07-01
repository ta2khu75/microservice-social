package dev.ta2khu75.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.ta2khu75.social.repository.IdentityClient;

@Configuration
public class WebClientConfig {
	@Bean
	WebClient webClient() {
		return WebClient.create("http://localhost:8081/identity-service");
	}

	@Bean
	IdentityClient identityClient() {
		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClient())).build();
		return httpServiceProxyFactory.createClient(IdentityClient.class);
	}
}
