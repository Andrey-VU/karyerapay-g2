package ru.karyeragame.paymentsystem.user.repository;

import ru.karyeragame.paymentsystem.user.dto.UserFullDto;

import java.util.List;

public interface UserRepository {
    UserFullDto create(UserFullDto userFullDto);

    UserFullDto update(Long id, UserFullDto userFullDto);

    UserFullDto getById(Long id);

    UserFullDto getByEmail(String email);

    List<UserFullDto> getAll();

    void delete(Long id);
}
