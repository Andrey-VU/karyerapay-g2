package ru.karyeragame.paymentsystem.account.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.karyeragame.paymentsystem.account.Type;
import ru.karyeragame.paymentsystem.account.dto.AccountDto;
import ru.karyeragame.paymentsystem.account.model.Account;
import ru.karyeragame.paymentsystem.common.exception.ObjectNotFoundException;
import ru.karyeragame.paymentsystem.exceptions.NotFoundException;
import ru.karyeragame.paymentsystem.exceptions.TransferException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountDbRepository implements AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Account getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(AccountRequests.GET_BY_ID, this::accountMapping, id);
        } catch (DataAccessException exception) {
            log.error("Счёт с ID {} не найден", id);
            throw new ObjectNotFoundException("Счёт", id);
        }
    }

    @Override
    @Transactional
    public void transfer(Long accountFrom, Long accountTo, BigDecimal amount) {
        try {
            int from = jdbcTemplate.update(AccountRequests.UPDATE_ACCOUNT_FROM, amount, accountFrom);
            int to = jdbcTemplate.update(AccountRequests.UPDATE_ACCOUNT_TO, amount, accountTo);
            if (from + to != 2) {
                throw new TransferException("Невозможно выполнить перевод со счёта: " + accountFrom +
                        " на счёт: " + accountTo + " на сумму: " + amount.toPlainString());
            }
        } catch (Exception exception) {
            log.error("Что-то пошло не так");
            throw new TransferException("Невозможно выполнить перевод со счёта: " + accountFrom +
                    " на счёт: " + accountTo + " на сумму: " + amount.toPlainString());
        }
    }

    @Override
    public Account create(Account account) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("accounts")
                .usingGeneratedKeyColumns("account_id");

        Map<String, Object> accountTable = new HashMap<>();
        accountTable.put("type", account.getType());
        accountTable.put("name", account.getName());
        accountTable.put("user_id", account.getUserId());
        accountTable.put("amount", account.getAmount());

        Long id = jdbcInsert.executeAndReturnKey(accountTable).longValue();
        return getById(id);
    }

    @Override
    public void deleteAccount(Long accountId) {
        if (getById(accountId) == null) {
            log.error("Счёт с ID {} не найден", accountId);
            throw new ObjectNotFoundException("Счёт", accountId);
        }
        jdbcTemplate.update(AccountRequests.DELETE, accountId);
    }

    @Override
    public List<Account> getAll() {
        return jdbcTemplate.query(AccountRequests.GET_ALL, this::accountMapping);
    }

    @Override
    public List<Account> getAllByEmail(String email) {
        try {
            return jdbcTemplate.query(AccountRequests.GET_BY_EMAIL, this::accountMapping, email);
        } catch (DataAccessException exception) {
            log.error("Нет счётов для email: " + email);
            throw new NotFoundException("Нет счётов для email: " + email);
        }
    }

    private Account accountMapping(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Account(resultSet.getLong("account_id"),
                Type.valueOf(resultSet.getString("type")),
                resultSet.getString("name"),
                resultSet.getLong("user_id"),
                resultSet.getBigDecimal("amount"));
    }

    private AccountDto accountDtoMapping(ResultSet resultSet, int rowNumber) throws SQLException {
        return new AccountDto(resultSet.getLong("account_id"),
                Type.valueOf(resultSet.getString("type")),
                resultSet.getString("name"),
                resultSet.getLong("user_id"),
                resultSet.getBigDecimal("amount"));
    }

}
