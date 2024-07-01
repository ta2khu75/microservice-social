package dev.ta2khu74.identity.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import dev.ta2khu74.identity.dto.request.AccountRequest;
import dev.ta2khu74.identity.dto.response.AccountResponse;
import dev.ta2khu74.identity.dto.response.ApiResponse;
import dev.ta2khu74.identity.exception.InvalidException;
import dev.ta2khu74.identity.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
	private final AccountService service;

	@PostMapping
	public ApiResponse<AccountResponse> create(@Validated @RequestBody AccountRequest account,
			BindingResult bindingResult) throws InvalidException {
		if (bindingResult.hasErrors()) {
			throw new InvalidException(bindingResult);
		}
		ApiResponse<AccountResponse> apiResponse = new ApiResponse<>();
		apiResponse.setData(service.create(account));
		return apiResponse;
	}

	@PutMapping("{accountId}")
	public ApiResponse<AccountResponse> update(@PathVariable Long accountId, @RequestBody AccountRequest account) {
		return ApiResponse.<AccountResponse>builder().data(service.update(accountId, account)).build();
	}

	@GetMapping("email/{email}")
	public ApiResponse<AccountResponse> findByEmail(@PathVariable String email) {
		return ApiResponse.<AccountResponse>builder().data(service.findByEmail(email)).build();
	}

	@GetMapping
	public ApiResponse<List<AccountResponse>> getAllAccounts() {
		return ApiResponse.<List<AccountResponse>>builder().data(service.getAllAccounts()).build();
	}

	@GetMapping("{id}")
	public ApiResponse<AccountResponse> findById(@PathVariable Long id) {
		return ApiResponse.<AccountResponse>builder().data(service.findById(id)).build();
	}
	@GetMapping("me")
	public ApiResponse<AccountResponse> getMe(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<AccountResponse>builder().data(service.findByEmail(authentication.getName())).build();
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

}
