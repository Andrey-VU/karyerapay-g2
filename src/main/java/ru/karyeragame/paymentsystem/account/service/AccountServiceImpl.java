package ru.karyeragame.paymentsystem.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.karyeragame.paymentsystem.account.dto.AccountDto;
import ru.karyeragame.paymentsystem.account.mapper.AccountMapper;
import ru.karyeragame.paymentsystem.account.model.Account;
import ru.karyeragame.paymentsystem.account.repository.AccountRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    /*todo исправить назначение стартового капитала после реализации создании игры*/

    private static final String START_CAPITAL = "1000";

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        /*todo mapping, возможно создание счёта привязать к созданию игры */
        Account account = Account.builder()
                .type(accountDto.getType())
                /*todo реализовать логику присвоения названия счёта в зависимости от имени пользователя и типа счёта*/
                .name(accountDto.getName())
                .userId(accountDto.getUserId())
                .amount(new BigDecimal(START_CAPITAL).setScale(2, RoundingMode.HALF_UP))
                .build();
        account = accountRepository.create(account);

        return AccountMapper.toAccountDto(account);
    }

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.getAll().stream()
                .map(AccountMapper::toAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccount(Long accountId) {
        return AccountMapper.toAccountDto(accountRepository.getById(accountId));
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountRepository.deleteAccount(accountId);
    }

    @Override
    public List<AccountDto> getAllByEmail(String email) {
        List<Account> accounts = accountRepository.getAllByEmail(email);
        return accounts.stream()
                .map(AccountMapper::toAccountDto)
                .collect(Collectors.toList());
    }
}
