package tech.build.agregadorinvestimento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.build.agregadorinvestimento.controller.dto.AccountResponseDto;
import tech.build.agregadorinvestimento.controller.dto.CreateAccountDto;
import tech.build.agregadorinvestimento.controller.dto.CreateUserDto;
import tech.build.agregadorinvestimento.controller.dto.UpdateUserDto;
import tech.build.agregadorinvestimento.entity.Account;
import tech.build.agregadorinvestimento.entity.User;
import tech.build.agregadorinvestimento.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        User newUser = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + newUser.getUserId().toString()))
                .body(newUser); // Adicione o usuário ao corpo da resposta
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> ShowUserById(@PathVariable("userId") String userId) {
        var user = userService.getUserByID(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsuarios();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable("userId") String userId,
                                              @RequestBody CreateAccountDto createAccountDto) {
        Account account = userService.createAccount(userId, createAccountDto);
        return ResponseEntity.ok().body(account);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> accountList(@PathVariable("userId") String userId) {
        var accounts = userService.accountList(userId);
        return ResponseEntity.ok(accounts);
    }
}
