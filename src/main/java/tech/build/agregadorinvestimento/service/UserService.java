package tech.build.agregadorinvestimento.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.build.agregadorinvestimento.controller.dto.AccountResponseDto;
import tech.build.agregadorinvestimento.controller.dto.CreateAccountDto;
import tech.build.agregadorinvestimento.controller.dto.CreateUserDto;
import tech.build.agregadorinvestimento.controller.dto.UpdateUserDto;
import tech.build.agregadorinvestimento.entity.Account;
import tech.build.agregadorinvestimento.entity.BillingAdrees;
import tech.build.agregadorinvestimento.entity.User;
import tech.build.agregadorinvestimento.repository.AccountRepository;
import tech.build.agregadorinvestimento.repository.BillingAdreesRepository;
import tech.build.agregadorinvestimento.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAdreesRepository billingAdreesRepository;


    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAdreesRepository billingAdreesRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAdreesRepository = billingAdreesRepository;
    }

    public User createUser(CreateUserDto createUserDto) {
        //DTO -> Entity
        var entity = new User(UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                null,
                Instant.now());
        var userSaved = userRepository.save(entity);
        return userSaved;

    }

    public void updateById(String userId, UpdateUserDto updateUserDto) {
        var userExist = userRepository.findById(UUID.fromString(userId));
        if (userExist.isPresent()) {
            var user = userExist.get();
            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }
    }

    public Optional<User> getUserByID(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsuarios() {
        return userRepository.findAll();
    }

    public void deleteById(String userId) {
        var userExist = userRepository.existsById(UUID.fromString(userId));

        if (userExist) {
            userRepository.deleteById(UUID.fromString(userId));
        }
    }

    public Account createAccount(String userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(UUID.fromString(userId)).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var account = new Account(
                UUID.randomUUID(),
                createAccountDto.accountName(),
                user,
                user.getUsername(),
                0.00,
                new ArrayList<>()
        );
        var accountcreated = accountRepository.save(account);
        return accountcreated;
    }

    public List<AccountResponseDto> accountList(String userId) {
        var user = userRepository.findById(UUID.fromString(userId)).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getAccounts().stream()
                .map(ac  -> new AccountResponseDto(ac.getAccountId().toString(),ac.getUserName(),ac.getAccountName(),ac.getBalance()))
            .toList();
    }
}
