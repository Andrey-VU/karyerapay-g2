package ru.karyeragame.paymentsystem.account.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.karyeragame.paymentsystem.account.dto.AccountDto;
import ru.karyeragame.paymentsystem.account.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/id/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccount(@PathVariable @Positive Long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping(("/email/{email}"))
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getAllByEmail(@PathVariable @Email String email) {
        return accountService.getAllByEmail(email);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable @Positive Long accountId) {
        accountService.deleteAccount(accountId);
    }

}
