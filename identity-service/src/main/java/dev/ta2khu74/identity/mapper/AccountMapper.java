package dev.ta2khu74.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.ta2khu74.identity.dto.request.AccountRequest;
import dev.ta2khu74.identity.dto.response.AccountResponse;
import dev.ta2khu74.identity.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", ignore = true)
	Account toEntity(AccountRequest accountRequest);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "email", ignore = true)
	void updateAccount(@MappingTarget Account accountTarget, AccountRequest accountSource);

	AccountResponse toResponse(Account account);
}
