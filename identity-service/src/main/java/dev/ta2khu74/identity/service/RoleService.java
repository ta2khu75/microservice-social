package dev.ta2khu74.identity.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.ta2khu74.identity.dto.request.RoleRequest;
import dev.ta2khu74.identity.dto.response.RoleResponse;
import dev.ta2khu74.identity.exception.NotFoundException;
import dev.ta2khu74.identity.mapper.RoleMapper;
import dev.ta2khu74.identity.model.Permission;
import dev.ta2khu74.identity.model.Role;
import dev.ta2khu74.identity.repository.PermissionRepository;
import dev.ta2khu74.identity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository repository;
	private final RoleMapper mapper;
	private final PermissionRepository permissionRepository;

	public RoleResponse save(RoleRequest roleRequest) {
		Role role = mapper.toModel(roleRequest);
		Set<Permission> permissions = new HashSet<Permission>(
				permissionRepository.findAllById(roleRequest.permissions()));
		role.setPermissions(permissions);
		return mapper.toResponse(repository.save(role));
	}

	public RoleResponse findById(String id) {
		return mapper.toResponse(repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Could not find role with name: " + id)));
	}

	public void deleteById(String id) {
		repository.deleteById(id);
	}

	public List<RoleResponse> getAll() {
		return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
	}
}
