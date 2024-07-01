package dev.ta2khu74.profile.model;

import java.time.LocalDate;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Node("profile")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	@Id @GeneratedValue
	Long id;
	Long accountId;
	String firstName;
	String lastName;
	LocalDate birthday;
	String address;
}
