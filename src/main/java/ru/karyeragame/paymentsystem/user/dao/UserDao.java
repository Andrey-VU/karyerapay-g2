package ru.karyeragame.paymentsystem.user.dao;

import ru.karyeragame.paymentsystem.user.model.User;

public interface UserDao {
    User findUserByEmail(String email);

    User findById(Long userId);

    Boolean saveUser(User user);

    Boolean deleteUserByEmail(String email);
    Boolean isExistUser(String email);
    Boolean updateUser(User user);

}
