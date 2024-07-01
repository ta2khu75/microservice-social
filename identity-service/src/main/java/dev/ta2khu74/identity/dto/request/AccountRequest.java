package dev.ta2khu74.identity.dto.request;

import java.time.LocalDate;
import java.util.List;

import dev.ta2khu74.identity.validator.BirthdayConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountRequest(@NotBlank @Email String email, @NotBlank String password, String confirmPassword,
		@NotBlank String firstName, @NotBlank String lastName, @NotNull @BirthdayConstraint(min = 12) LocalDate birthday, String address, List<String> roles) {
}