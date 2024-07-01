package dev.ta2khu74.identity.security;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import dev.ta2khu74.identity.exception.UnAuthenticatedException;
import dev.ta2khu74.identity.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
	@Value("${jwt.signature}")
	private String jwtSignature	;
	private final InvalidatedTokenRepository invalidatedTokenRepository;
//	@Autowired
//	private AuthenticationService authenticationService;
	private NimbusJwtDecoder nimbusJwtDecoder = null;


	@Override
	public Jwt decode(String token) throws JwtException {

		try {
			verifyToken(token);
//			boolean response =

//			if (!response)
//				throw new JwtException("Token invalid");
		} catch (JOSEException | ParseException e) {
			throw new JwtException(e.getMessage());
		}
		if (Objects.isNull(nimbusJwtDecoder)) {
			SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSignature.getBytes(), "HS512");
			nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
		}

		return nimbusJwtDecoder.decode(token);
	}
	public  SignedJWT verifyToken(String token) throws JOSEException, ParseException {
		JWSVerifier verifier = new MACVerifier(jwtSignature.getBytes());
		SignedJWT signedJWT = SignedJWT.parse(token);
		Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		boolean verified = signedJWT.verify(verifier);
		if (!verified || expityTime.before(new Date())) {
			throw new UnAuthenticatedException("unauthorized");
		}
		if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
			throw new UnAuthenticatedException("unauthorized");
		}
		return signedJWT;
	}
}