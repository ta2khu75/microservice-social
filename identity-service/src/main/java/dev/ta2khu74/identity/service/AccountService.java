package dev.ta2khu74.identity.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.ta2khu74.identity.dto.request.AccountRequest;
import dev.ta2khu74.identity.dto.response.AccountResponse;
import dev.ta2khu74.identity.exception.ExistingException;
import dev.ta2khu74.identity.exception.NotFoundException;
import dev.ta2khu74.identity.mapper.AccountMapper;
import dev.ta2khu74.identity.mapper.ProfileMapper;
import dev.ta2khu74.identity.model.Account;
import dev.ta2khu74.identity.model.Role;
import dev.ta2khu74.identity.repository.AccountRepository;
import dev.ta2khu74.identity.repository.RoleRepository;
import dev.ta2khu74.identity.repository.http.ProfileClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository repository;
	private final AccountMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final ProfileClient profileClient;
	private final ProfileMapper profileMapper;
	public AccountResponse create(AccountRequest accountRes) {
		Account account = repository.findByEmail(accountRes.email()).orElse(new Account());
		if (account.getId() != null) {
			throw new ExistingException("Email already exists");
		}
		
		Set<Role> roles = new HashSet<>();
		if (accountRes.roles() != null) {
			roles.addAll(roleRepository.findAllById(accountRes.roles()));
		} else {
			roles.add(roleRepository.findById("USER")
					.orElseThrow(() -> new NotFoundException("Could not find role with name: USER")));
		}
		account=mapper.toEntity(accountRes);
		account.setRoles(roles);
		account.setPassword(passwordEncoder.encode(accountRes.password()));
//		account=repository.save(account);
		profileClient.create(profileMapper.toProfileRes(repository.save(account)));
		return mapper.toResponse(repository.save(account));
	}

	public AccountResponse findByEmail(String email) {

		return mapper.toResponse(repository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Could not find account with email: " + email)));
	}

	@PreAuthorize("hasAuthority('CREATE_POST')")
	public AccountResponse update(Long accountId, AccountRequest account) {
		Account accountExisting = repository.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Could not find account with id: " + accountId));
		Set<Role> roles = new HashSet<>(roleRepository.findAllById(account.roles()));
		if (roles.isEmpty()) {
			roles.add(roleRepository.findById("USER")
					.orElseThrow(() -> new NotFoundException("Could not find role with name: USER")));
		}
		mapper.updateAccount(accountExisting, account);
		accountExisting.setRoles(roles);
		return mapper.toResponse(repository.save(accountExisting));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	@PostAuthorize("returnObject.email==authentication.name || hasRole('ADMIN')")
	public AccountResponse findById(Long id) {
		return mapper.toResponse(repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Could not find account with id: " + id)));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<AccountResponse> getAllAccounts() {
//		Set<Role> roles = new HashSet<>();
//		roles.add(Role.USER);
//
//		repository.findAll().forEach(account -> {
//			if (account.getEmail() != null && account.getEmail().equals(("m@g.com"))) {
//				System.out.println("fdsafdfsd");
//				Set<Role> roless = new HashSet<>();
//				roless.add(Role.ADMIN);
//				roless.add(Role.USER);
//				account.setRoles(roless);
//				repository.save(account);
//			}
//		});
//			account.setPassword(passwordEncoder.encode(account.getPassword()));
//		});
		return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
	}
}
