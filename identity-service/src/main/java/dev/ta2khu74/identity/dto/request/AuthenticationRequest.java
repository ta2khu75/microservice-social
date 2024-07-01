package dev.ta2khu74.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest (@NotBlank @Email String email, @NotBlank String password){
}
