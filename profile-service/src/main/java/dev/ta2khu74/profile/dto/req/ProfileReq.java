package dev.ta2khu74.profile.dto.req;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileReq(@NotNull Long accountId, @NotBlank String firstName, @NotBlank String lastName, @NotNull LocalDate birthday, String address) {
}
