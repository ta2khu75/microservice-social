package dev.ta2khu74.profile.mapper;

import org.mapstruct.Mapper;

import dev.ta2khu74.profile.dto.req.ProfileReq;
import dev.ta2khu74.profile.dto.res.ProfileRes;
import dev.ta2khu74.profile.model.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	Profile toModel(ProfileReq profileRequest);
	ProfileRes toRes(Profile profile);
}
