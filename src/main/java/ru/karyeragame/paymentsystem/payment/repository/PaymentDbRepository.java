package ru.karyeragame.paymentsystem.payment.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.karyeragame.paymentsystem.common.exception.ObjectNotFoundException;
import ru.karyeragame.paymentsystem.exceptions.NotFoundException;
import ru.karyeragame.paymentsystem.payment.model.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentDbRepository implements PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Payment create(Payment payment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("payments")
                .usingGeneratedKeyColumns("payments_id");

        Map<String, Object> paymentTable = new HashMap<>();
        paymentTable.put("account_id_from", payment.getFromAccount());
        paymentTable.put("account_id_to", payment.getToAccount());
        paymentTable.put("amount", payment.getAmount());
        paymentTable.put("created_on", payment.getCreated());

        Long id = jdbcInsert.executeAndReturnKey(paymentTable).longValue();
        return getById(id);
    }

    @Override
    public Payment getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(PaymentRequests.GET_BY_ID, this::paymentMapping, id);
        } catch (DataAccessException exception) {
            log.error("Пользователь с ID {} не найден", id);
            throw new ObjectNotFoundException("Пользователь", id);
        }
    }

    @Override
    public List<Payment> getAll() {
        try {
            return jdbcTemplate.query(PaymentRequests.GET_ALL, this::paymentMapping);
        } catch (DataAccessException exception) {
            log.error("Нет счётов");
            throw new NotFoundException("Нет счётов");
        }
    }

    @Override
    public List<Payment> getAllByEmail(String email) {
        try {
            return jdbcTemplate.query(PaymentRequests.GET_BY_EMAIL, this::paymentMapping, email);
        } catch (DataAccessException exception) {
            log.error("Нет платежей по email: " + email);
            throw new NotFoundException("Нет платежей по email: " + email);
        }
    }

    private Payment paymentMapping(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Payment(resultSet.getLong("payments_id"),
                resultSet.getLong("account_id_from"),
                resultSet.getLong("account_id_to"),
                resultSet.getBigDecimal("amount"),
                resultSet.getTimestamp("created_on").toLocalDateTime());
    }
}
