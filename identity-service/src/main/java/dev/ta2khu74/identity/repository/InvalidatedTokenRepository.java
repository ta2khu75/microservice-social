package dev.ta2khu74.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ta2khu74.identity.model.InvalidatedToken;
@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}