package dev.ta2khu74.identity.dto.response;

import java.time.LocalDate;
import java.util.Set;

import dev.ta2khu74.identity.model.Role;

public record AccountResponse(Long id,String email, String firstName, String lastName, LocalDate birthday, String address, Set<Role> roles) {
}
