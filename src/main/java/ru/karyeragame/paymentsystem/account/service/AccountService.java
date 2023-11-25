package ru.karyeragame.paymentsystem.account.service;

import ru.karyeragame.paymentsystem.account.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    List<AccountDto> getAll();

    AccountDto getAccount(Long accountId);

    void deleteAccount(Long accountId);

    List<AccountDto> getAllByEmail(String email);
}
