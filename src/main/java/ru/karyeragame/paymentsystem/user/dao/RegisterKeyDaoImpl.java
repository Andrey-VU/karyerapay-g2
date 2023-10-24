package ru.karyeragame.paymentsystem.user.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.karyeragame.paymentsystem.user.model.RegisterKey;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Objects;

@Component
@AllArgsConstructor
public class RegisterKeyDaoImpl implements RegisterKeyDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean deleteRegisterKey(String key) {
        int c = jdbcTemplate.update("delete from REGISTER_KEYS where hash_key = ?", key);
        return c > 0;
    }

    @Override
    public RegisterKey findRegisterKeyByEmail(String email) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from REGISTER_KEYS where email = ?", email);
        return getRegisterKey(userRows);
    }

    @Override
    public boolean saveRegisterKey(RegisterKey registerKey) {
        String sqlQuery = "insert into REGISTER_KEYS(HASH_KEY, EMAIL, USER_ID, EXP) values (?, ?, ?, ?)";
        int c = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, registerKey.getKey());
            stmt.setString(2, registerKey.getEmail());
            stmt.setLong(3, registerKey.getUserId());
            stmt.setTimestamp(4, Timestamp.valueOf(registerKey.getExp()));
            return stmt;
        });
        return c > 0;
    }

    private RegisterKey getRegisterKey(SqlRowSet userRows) {
        if (userRows.next()) {
            RegisterKey registerKey = RegisterKey.builder().
                    id(userRows.getLong("register_key_id")).
                    key(userRows.getString("hash_key")).
                    email(userRows.getString("email")).
                    userId(userRows.getLong("user_id")).
                    exp(Objects.requireNonNull(userRows.getTimestamp("exp")).toLocalDateTime()).
                    build();
            return registerKey;
        } else {
            return RegisterKey.builder().build();
        }
    }

}
