package ru.karyeragame.paymentsystem.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.karyeragame.paymentsystem.user.dto.UserDtoRegistration;
import ru.karyeragame.paymentsystem.user.model.User;
import ru.karyeragame.paymentsystem.user.service.CustomUserService;
import ru.karyeragame.paymentsystem.user.service.UserService;

@RestController
@RequestMapping("/karyerapay")
@RequiredArgsConstructor

public class UserRegistration {
    private final UserService userService;
    private final CustomUserService userDetailsService;

    // для проверки сохранения в базе, на удаление
    @GetMapping
    @RequestMapping("/registration/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@NotBlank @PathVariable String email) {
        return (User) userDetailsService.loadUserByUsername(email);
    }

    @PostMapping
    @RequestMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody @Valid UserDtoRegistration userDtoRegistration) {
        return userService.createUser(userDtoRegistration);
    }

    @PostMapping
    @RequestMapping("/confirmation")
    @ResponseStatus(HttpStatus.CREATED)
    public String confirmationUser(@NotBlank @RequestParam(name = "key") String key,
                                   @NotBlank @RequestParam(name = "email") String email) {
        return userService.confirmationRegistration(key, email);
    }
}