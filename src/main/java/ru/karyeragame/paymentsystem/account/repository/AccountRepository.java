package ru.karyeragame.paymentsystem.account.repository;

import ru.karyeragame.paymentsystem.account.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {
    Account getById(Long id);

    void transfer(Long accountFrom, Long accountTo, BigDecimal amount);

    Account create(Account account);

    void deleteAccount(Long accountId);

    List<Account> getAll();

    List<Account> getAllByEmail(String email);
}
