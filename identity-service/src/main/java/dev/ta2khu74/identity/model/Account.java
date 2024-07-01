package dev.ta2khu74.identity.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8_unicode_ci")	
	String email;
	String password;
	String firstName;
	String lastName;
	LocalDate birthday;
	String address;
	@ManyToMany
	Set<Role> roles;
}
