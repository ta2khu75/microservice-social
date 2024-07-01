package dev.ta2khu74.profile.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.ta2khu74.profile.dto.req.ProfileReq;
import dev.ta2khu74.profile.dto.res.ProfileRes;
import dev.ta2khu74.profile.exception.NotFoundException;
import dev.ta2khu74.profile.mapper.ProfileMapper;
import dev.ta2khu74.profile.model.Profile;
import dev.ta2khu74.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService{
	ProfileRepository repository;
	ProfileMapper mapper;
	@Override
	public ProfileRes create(ProfileReq req) {
		Profile profile=mapper.toModel(req);
		return mapper.toRes(repository.save(profile));
	}

	@Override
	public ProfileRes update(Long id, ProfileReq req) {
		Profile profileExisting=repository.findById(id).orElseThrow(()->new NotFoundException("Could not find profile with id: "+id));
		Profile profile=mapper.toModel(req);
		if(profile.getAddress()==null) {
			profile.setAddress(profileExisting.getAddress());
		}
		return mapper.toRes(repository.save(profile));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public ProfileRes findById(Long id) {
		return mapper.toRes(repository.findById(id).orElseThrow(()->new NotFoundException("Could not find profile with id: "+id)));
	}

	@Override
	public List<ProfileRes> findAll() {
		return repository.findAll().stream().map(t ->mapper.toRes(t)).collect(Collectors.toList());
	}
}
