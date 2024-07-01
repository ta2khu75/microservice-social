package dev.ta2khu74.identity.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	String name;
	String description;
	@ManyToMany
	Set<Permission> permissions;
}
