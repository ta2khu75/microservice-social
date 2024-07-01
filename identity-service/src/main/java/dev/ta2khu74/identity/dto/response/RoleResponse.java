package dev.ta2khu74.identity.dto.response;

import java.util.Set;

public record RoleResponse(String name, Set<String> permissions) {

}
