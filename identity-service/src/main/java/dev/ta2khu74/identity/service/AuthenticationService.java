package dev.ta2khu74.identity.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.ta2khu74.identity.dto.request.AuthenticationRequest;
import dev.ta2khu74.identity.dto.request.IntrospectRequest;
import dev.ta2khu74.identity.dto.request.LogoutRequest;
import dev.ta2khu74.identity.dto.request.RefreshRequest;
import dev.ta2khu74.identity.dto.response.AuthenticationResponse;
import dev.ta2khu74.identity.exception.NotFoundException;
import dev.ta2khu74.identity.exception.UnAuthenticatedException;
import dev.ta2khu74.identity.model.Account;
import dev.ta2khu74.identity.model.InvalidatedToken;
import dev.ta2khu74.identity.model.Role;
import dev.ta2khu74.identity.repository.AccountRepository;
import dev.ta2khu74.identity.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
	private final AccountRepository accountRepository;
	private final InvalidatedTokenRepository invalidatedTokenRepository;
	private final PasswordEncoder passwordEncoder;
	@Value("${jwt.signature}")
	private String jwtSignature;
	@Value("${jwt.expiration}")
	private long jwtExpiration;
	@Value("${jwt.refresh.expiration}")
	private long jwtRefreshExpiration;

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		log.info("login loading");
		Account account = accountRepository.findByEmail(authenticationRequest.email()).orElse(null);
		if (account == null || passwordEncoder.matches(account.getPassword(), authenticationRequest.password())) {
			throw new UnAuthenticatedException("Email or password incorrect");
		}
		String jwtToken = generateToken(account);
		return new AuthenticationResponse(jwtToken, true);
	}

	private String generateToken(Account account) {
		// dinh nghia thua toan header
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
		// Dinh nghia content claim
		JWTClaimsSet jwsClaimsSet = new JWTClaimsSet.Builder().subject(account.getEmail()).issuer("dev.ta2khu75")
				.issueTime(new Date()).expirationTime(new Date(Instant.now().plus(jwtExpiration, ChronoUnit.SECONDS).toEpochMilli()))
				.jwtID(UUID.randomUUID().toString()).claim("scope", joinRole(account.getRoles())).build();
		Payload payload = new Payload(jwsClaimsSet.toJSONObject());
		JWSObject jwsObject = new JWSObject(jwsHeader, payload);
		try {
			jwsObject.sign(new MACSigner(jwtSignature.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private SignedJWT verifyToken(String token, boolean isRefreshToken) throws JOSEException, ParseException {
		JWSVerifier verifier = new MACVerifier(jwtSignature.getBytes());
		SignedJWT signedJWT = SignedJWT.parse(token);
		Date expityTime = isRefreshToken
				? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
						.plus(jwtRefreshExpiration, ChronoUnit.SECONDS).toEpochMilli())
				: signedJWT.getJWTClaimsSet().getExpirationTime();
		boolean verified = signedJWT.verify(verifier);
		if (!verified || expityTime.before(new Date())) {
			throw new UnAuthenticatedException("unauthorized");
		}
		if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
			throw new UnAuthenticatedException("unauthorized");
		}
		System.out.println("true");
		return signedJWT;
	}

	public boolean introspect(IntrospectRequest introspect) throws JOSEException, ParseException {
		try {
			verifyToken(introspect.token(),false);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws JOSEException, ParseException {
		SignedJWT signedJWT = verifyToken(refreshRequest.token(), true);
		String jti = signedJWT.getJWTClaimsSet().getJWTID();
		Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		InvalidatedToken invalidatedToken = new InvalidatedToken(jti, expityTime);
		invalidatedTokenRepository.save(invalidatedToken);
		String email = signedJWT.getJWTClaimsSet().getSubject();
		Account account = accountRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Could not find account with email: " + email));
		String jwtToken = generateToken(account);
		return new AuthenticationResponse(jwtToken, true);
	}

	public String joinRole(Set<Role> roles) {
		StringJoiner joiner = new StringJoiner(" ");
		if (!CollectionUtils.isEmpty(roles)) {
			roles.forEach(role -> {
				joiner.add("ROLE_" + role.getName());
				if (!CollectionUtils.isEmpty(role.getPermissions())) {
					role.getPermissions().forEach(permission -> {
						joiner.add(permission.getName());
					});
				}
			});
		}
		return joiner.toString();
	}

	public void logout(LogoutRequest request) throws JOSEException, ParseException {
		try	{
		SignedJWT signedJWT = verifyToken(request.token(), true);
		String jti = signedJWT.getJWTClaimsSet().getJWTID();
		Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		InvalidatedToken invalidatedToken = new InvalidatedToken(jti, expityTime);
		invalidatedTokenRepository.save(invalidatedToken);
		}catch (Exception e) {
			log.info("Token already expired");
		}
	}
}
