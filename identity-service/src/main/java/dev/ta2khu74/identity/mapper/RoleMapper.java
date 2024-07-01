package dev.ta2khu74.identity.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.ta2khu74.identity.dto.request.RoleRequest;
import dev.ta2khu74.identity.dto.response.RoleResponse;
import dev.ta2khu74.identity.model.Permission;
import dev.ta2khu74.identity.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	@Mapping(target = "permissions", ignore = true)
	Role toModel(RoleRequest roleRequest);

	@Mapping(target = "permissions", ignore = true)
	void updateRole(RoleRequest roleRequest, @MappingTarget Role role);

	RoleResponse toResponse(Role role);

	default Set<String> mapPermissionToStrings(Set<Permission> permissions) {
		return permissions.stream().map(Permission::getName).collect(Collectors.toSet());
	}
}
