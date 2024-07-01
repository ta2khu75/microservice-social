package dev.ta2khu74.identity.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ta2khu74.identity.dto.request.PermissionRequest;
import dev.ta2khu74.identity.dto.response.ApiResponse;
import dev.ta2khu74.identity.dto.response.PermissionResponse;
import dev.ta2khu74.identity.exception.InvalidException;
import dev.ta2khu74.identity.service.PermissionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
	private final PermissionService service;

	@PostMapping
	public ApiResponse<PermissionResponse> create(@Validated @RequestBody PermissionRequest permission,
			BindingResult bindingResult) throws InvalidException {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(bindingResult);
		}
		return ApiResponse.<PermissionResponse>builder().data(service.save(permission)).build();
	}

	@GetMapping
	public ApiResponse<List<PermissionResponse>> getAll() {
		return ApiResponse.<List<PermissionResponse>>builder().data(service.getAll()).build();
	}

	@GetMapping("{id}")
	public ApiResponse<PermissionResponse> read(@PathVariable String id) {
		return ApiResponse.<PermissionResponse>builder().data(service.findById(id)).build();
	}

	@DeleteMapping("{id}")
	public ApiResponse<?> delete(@PathVariable String id) {
		service.deleteById(id);
		return new ApiResponse<>();
	}

	@PutMapping
	public ApiResponse<PermissionResponse> update(@Validated @RequestBody PermissionRequest permission,
			BindingResult bindingResult) throws InvalidException {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(bindingResult);
		}
		return ApiResponse.<PermissionResponse>builder().data(service.save(permission)).build();
	}

}
