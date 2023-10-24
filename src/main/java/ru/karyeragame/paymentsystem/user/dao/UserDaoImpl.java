package ru.karyeragame.paymentsystem.user.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.karyeragame.paymentsystem.exceptions.ExceptionNotFound;
import ru.karyeragame.paymentsystem.user.Profession;
import ru.karyeragame.paymentsystem.user.Role;
import ru.karyeragame.paymentsystem.user.Status;
import ru.karyeragame.paymentsystem.user.model.User;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User findUserByEmail(String email) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where email = ?", email);
        return getUser(userRows);
    }

    @Override
    public User findById(Long userId) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where USER_ID = ?", userId);
        return getUser(userRows);
    }

    @Override
    public Boolean saveUser(User user) {
        String sqlQuery = "insert into USERS(nickname, email, password, created_on, role, profession, status) values (?, ?, ?, ?, ?, ?, ?)";
        int count = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setTimestamp(4, Timestamp.valueOf(user.getCreated()));
            stmt.setString(5, String.valueOf(user.getRole()));
            stmt.setString(6, String.valueOf(user.getProfession()));
            stmt.setString(7, String.valueOf(user.getStatus()));
            return stmt;
        });
        return count > 0;
    }

    @Override
    public Boolean updateUser(User user) {
        String sqlQuery = "update  USERS set nickname=?, email=?, password=?, created_on=?, role=?, profession=?, status=? where user_id=?";
        int count = jdbcTemplate.update(sqlQuery, user.getName(), user.getEmail(), user.getPassword(), Timestamp.valueOf(user.getCreated()), String.valueOf(user.getRole()), String.valueOf(user.getProfession()), String.valueOf(user.getStatus()), user.getId());
        return count > 0;
    }

    @Override
    public Boolean deleteUserByEmail(String email) {
        int count = jdbcTemplate.update("delete from USERS where EMAIL = ?", email);
        return count > 0;
    }

    @Override
    public Boolean isExistUser(String email) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where email = ?", email);
        if (userRows.next()) {
            String result = userRows.getString("email");
            assert result != null;
            return result.equals(email);
        }
        return false;
    }

    private User getUser(SqlRowSet userRows) {
        if (userRows.next()) {
            User user = User.builder().
                    id(userRows.getLong("user_id")).
                    name(userRows.getString("nickname")).
                    email(userRows.getString("email")).
                    password(userRows.getString("password")).
                    created(Objects.requireNonNull(userRows.getTimestamp("created_on")).toLocalDateTime()).
                    role(Role.valueOf(Objects.requireNonNull(userRows.getString("role")).toUpperCase())).
                    profession(Profession.valueOf(Objects.requireNonNull(userRows.getString("profession")).toUpperCase())).
                    status(Status.valueOf(Objects.requireNonNull(userRows.getString("status")).toUpperCase())).
                    build();
            return user;
        } else {
            throw new ExceptionNotFound("Запись о пользователе отсутствует.");
        }
    }
}
