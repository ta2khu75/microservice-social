package dev.ta2khu74.identity.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@RequiredArgsConstructor
public class SecurityConfig {
	private final String[] PUBLIC_POST_ENDPOINT = { "/auth/login", "/auth/introspect", "/auth/logout" , "/auth/refresh", "/accounts"};
//	@Value("${jwt.signature}")
//	private String jwtSignature;
	@Autowired
	private CustomJwtDecoder jwtDecoder;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// filter
		http.csrf(t -> t.disable()).authorizeHttpRequests(
				authorize -> authorize.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINT).permitAll()
//						.requestMatchers(HttpMethod.GET, "/accounts").hasRole(Role.ADMIN.name())
						.anyRequest().authenticated());

		// verifile jwt token header auth bearer
		http.oauth2ResourceServer(oauth2 -> oauth2 // and jwt converter
				.jwt(token -> token.decoder(jwtDecoder).jwtAuthenticationConverter(jwtConverter()))
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
		return http.build();
	}

	private JwtAuthenticationConverter jwtConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

//	public JwtDecoder jwtDecoder() {
//		SecretKeySpec jwtSecretSpec = new SecretKeySpec(jwtSignature.getBytes(), JwsAlgorithms.HS512);
//		return NimbusJwtDecoder.withSecretKey(jwtSecretSpec).macAlgorithm(MacAlgorithm.HS512).build();
//	}
}
