package dev.ta2khu74.identity.repository.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import dev.ta2khu74.identity.dto.request.ProfileReq;
import dev.ta2khu74.identity.dto.response.ProfileRes;

@FeignClient(name = "profile-service", url = "${app.service.profile}")
public interface ProfileClient {
	@PostMapping(value = "/internal", produces = MediaType.APPLICATION_JSON_VALUE)
	ProfileRes create(ProfileReq profileReq);
}
