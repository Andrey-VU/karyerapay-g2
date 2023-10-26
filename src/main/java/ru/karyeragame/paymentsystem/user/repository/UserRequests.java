package ru.karyeragame.paymentsystem.user.repository;

public class UserRequests {
    static final String UPDATE = "UPDATE users " +
            "SET " +
            "nickname = ?, " +
            "email = ?, " +
            "password = ?, " +
            "WHERE user_id = ?";

    static final String GET_BY_ID = "SELECT * " +
            "FROM users " +
            "WHERE user_id = ?";

    static final String GET_BY_EMAIL = "SELECT * " +
            "FROM users " +
            "WHERE email = ?";

    static final String GET_ALL = "SELECT * " +
            "FROM users";

    static final String DELETE = "DELETE " +
            "FROM users " +
            "WHERE user_id = ?";
}
