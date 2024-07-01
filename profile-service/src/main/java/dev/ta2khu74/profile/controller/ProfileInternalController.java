package dev.ta2khu74.profile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ta2khu74.profile.dto.req.ProfileReq;
import dev.ta2khu74.profile.dto.res.ProfileRes;
import dev.ta2khu74.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/internal")
public class ProfileInternalController {
	ProfileService service;

	@PostMapping
	public ProfileRes postMethodName(@RequestBody ProfileReq req ) {
		return service.create(req);
	}
}
