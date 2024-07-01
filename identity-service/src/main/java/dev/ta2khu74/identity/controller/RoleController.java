package dev.ta2khu74.identity.controller;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ta2khu74.identity.dto.request.RoleRequest;
import dev.ta2khu74.identity.dto.response.ApiResponse;
import dev.ta2khu74.identity.dto.response.RoleResponse;
import dev.ta2khu74.identity.exception.InvalidException;
import dev.ta2khu74.identity.service.RoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService service;

	@PostMapping
	public ApiResponse<RoleResponse> create(@Validated @RequestBody RoleRequest role, BindingResult bindingResult)
			throws InvalidException {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(bindingResult);
		}
		return ApiResponse.<RoleResponse>builder().data(service.save(role)).build();
	}

	@GetMapping("{id}")
	public ApiResponse<RoleResponse> read(@PathVariable String id) {
		return ApiResponse.<RoleResponse>builder().data(service.findById(id)).build();
	}
	
	@DeleteMapping("{id}")
	public ApiResponse<?> delete(@PathVariable String id) {
        service.deleteById(id);
        return new ApiResponse<>();
    }
	@GetMapping
	public ApiResponse<List<RoleResponse>> getAll() {
		return ApiResponse.<List<RoleResponse>>builder().data(service.getAll()).build();
	}
}
