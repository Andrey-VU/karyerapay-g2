package ru.karyeragame.paymentsystem.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.karyeragame.paymentsystem.common.exception.ObjectNotFoundException;
import ru.karyeragame.paymentsystem.user.Role;
import ru.karyeragame.paymentsystem.user.dto.UserFullDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userDbRepository")
@RequiredArgsConstructor
@Slf4j
public class UserDbRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserFullDto create(UserFullDto userFullDto) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");

        Map<String, Object> userTable = new HashMap<>();
        userTable.put("nickname", userFullDto.getNickname());
        userTable.put("email", userFullDto.getEmail());
        userTable.put("password", userFullDto.getPassword());
        userTable.put("role", Role.USER.toString());
        userTable.put("created_on", LocalDateTime.now());
        /**
         * Здесь вопрос по роли пользователя, как назначать?
         * Либо добавить сюда профессии
         */

        Long id = jdbcInsert.executeAndReturnKey(userTable).longValue();
        return getById(id);
    }

    @Override
    public UserFullDto update(Long id, UserFullDto userFullDto) {
        jdbcTemplate.update(UserRequests.UPDATE, userFullDto.getNickname(), userFullDto.getEmail(),
                userFullDto.getPassword(), id);
        return getById(id);
    }

    @Override
    public UserFullDto getById(Long id) {
        try {
            return jdbcTemplate.queryForObject(UserRequests.GET_BY_ID, this::userMapping, id);
        } catch (DataAccessException exception) {
            log.error("Пользователь с ID {} не найден", id);
            throw new ObjectNotFoundException("Пользователь", id);
        }
    }

    @Override
    public UserFullDto getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(UserRequests.GET_BY_EMAIL, this::userMapping, email);
        } catch (DataAccessException exception) {
            log.error("Пользователь с EMAIL {} не найден", email);
            throw new ObjectNotFoundException(String.format("Пользователь с EMAIL %s", email), 404L);
        }
    }

    @Override
    public List<UserFullDto> getAll() {
        return jdbcTemplate.query(UserRequests.GET_ALL, this::userMapping);
    }

    @Override
    public void delete(Long id) {
        if (getById(id) == null) {
            log.error("Пользователь с ID {} не найден", id);
            throw new ObjectNotFoundException("Пользователь", id);
        }
        jdbcTemplate.update(UserRequests.DELETE, id);
    }

    private UserFullDto userMapping(ResultSet resultSet, int rowNumber) throws SQLException {
        return new UserFullDto(resultSet.getLong("user_id"),
                resultSet.getString("nickname"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                Role.valueOf(resultSet.getString("role")));
    }
}
