package dev.ta2khu74.profile.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import dev.ta2khu74.profile.model.Profile;
@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, Long>{

}
