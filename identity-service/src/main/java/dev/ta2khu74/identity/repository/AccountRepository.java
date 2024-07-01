package dev.ta2khu74.identity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ta2khu74.identity.model.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByEmail(String email);
}
