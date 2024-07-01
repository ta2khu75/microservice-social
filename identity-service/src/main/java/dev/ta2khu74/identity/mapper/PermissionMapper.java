package dev.ta2khu74.identity.mapper;

import org.mapstruct.Mapper;

import dev.ta2khu74.identity.dto.request.PermissionRequest;
import dev.ta2khu74.identity.dto.response.PermissionResponse;
import dev.ta2khu74.identity.model.Permission;
@Mapper(componentModel = "spring")
public interface PermissionMapper {
	Permission toModel(PermissionRequest permissionRequest);
	PermissionRequest toRequest(Permission permission);
	PermissionResponse toResponse(Permission permission);
}
