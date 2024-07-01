package dev.ta2khu74.identity.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RoleRequest (@NotBlank String name, String description, @NotEmpty Set<String> permissions){

}
