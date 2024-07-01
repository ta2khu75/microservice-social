package dev.ta2khu74.profile.dto.res;

import java.time.LocalDate;

public record ProfileRes(Long id, Long accountId, String firstName, String lastName, LocalDate birthday, String address) {

}
