package dev.ta2khu74.profile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ta2khu74.profile.dto.req.ProfileReq;
import dev.ta2khu74.profile.dto.res.ProfileRes;
import dev.ta2khu74.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {
	ProfileService service;
	@GetMapping
    public List<ProfileRes> findAll(){
        return service.findAll();
    }
	@GetMapping("{id}")
	public ProfileRes getMethodName(@PathVariable Long id) {
		return service.findById(id);
	}
	@PutMapping("{id}")
	public ProfileRes putMethodName(@PathVariable Long id, @RequestBody ProfileReq req) {
		return service.update(id, req);
	}
	@DeleteMapping("{id}")
    public void deleteMethodName(@PathVariable Long id) {
        service.delete(id);
    }
	
}
