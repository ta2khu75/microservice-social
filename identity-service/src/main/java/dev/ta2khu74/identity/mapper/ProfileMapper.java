package dev.ta2khu74.identity.mapper;

import org.mapstruct.Mapper;

import dev.ta2khu74.identity.dto.request.ProfileReq;
import dev.ta2khu74.identity.model.Account;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	@Mapping(source = "id", target = "accountId")
	ProfileReq toProfileRes(Account account);	
}
