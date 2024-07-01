package dev.ta2khu74.profile.service;

import java.util.List;

import dev.ta2khu74.profile.dto.req.ProfileReq;
import dev.ta2khu74.profile.dto.res.ProfileRes;

public interface ProfileService {
	ProfileRes create(ProfileReq req);
	ProfileRes update(Long id, ProfileReq req);
	void delete(Long id);
	ProfileRes findById(Long id);
	List<ProfileRes> findAll();
}
