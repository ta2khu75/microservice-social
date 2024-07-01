package dev.ta2khu74.identity;

import java.security.SecureRandom;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IdentityServiceApplicationTests {
	private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

	public static String generateNewSecret() {
        byte[] randomBytes = new byte[64]; // 64 bytes = 512 bits
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

	@Test
	void contextLoads() {
		String newSecret = generateNewSecret();
        System.out.println("New Secret Key: " + newSecret);
	}

}
