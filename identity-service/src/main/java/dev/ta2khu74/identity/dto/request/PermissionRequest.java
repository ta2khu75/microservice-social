package dev.ta2khu74.identity.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(@NotBlank String name, String description) {

}
