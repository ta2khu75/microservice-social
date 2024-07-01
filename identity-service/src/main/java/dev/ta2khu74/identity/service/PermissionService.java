package dev.ta2khu74.identity.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.ta2khu74.identity.dto.request.PermissionRequest;
import dev.ta2khu74.identity.dto.response.PermissionResponse;
import dev.ta2khu74.identity.exception.NotFoundException;
import dev.ta2khu74.identity.mapper.PermissionMapper;
import dev.ta2khu74.identity.model.Permission;
import dev.ta2khu74.identity.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final PermissionRepository repository;
	private final PermissionMapper mapper;

	public PermissionResponse save(PermissionRequest permissionRequest) {
		Permission permission = mapper.toModel(permissionRequest);
		return mapper.toResponse(repository.save(permission));
	}

	public PermissionResponse findById(String id) {
		return mapper.toResponse(repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Could not find permission with id: " + id)));
	}

	public void deleteById(String id) {
		repository.deleteById(id);
	}

	public List<PermissionResponse> getAll() {
		return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
	}
}
