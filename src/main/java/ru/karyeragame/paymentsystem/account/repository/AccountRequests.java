package ru.karyeragame.paymentsystem.account.repository;

public class AccountRequests {
    static final String UPDATE_ACCOUNT_FROM = "UPDATE accounts " +
            "SET amount = amount - ? " +
            "WHERE account_id = ?";

    static final String UPDATE_ACCOUNT_TO = "UPDATE accounts " +
            "SET amount = amount + ? " +
            "WHERE account_id = ?";

    static final String GET_BY_ID = "SELECT * " +
            "FROM accounts " +
            "WHERE account_id = ?";

    static final String GET_BY_EMAIL = "SELECT a.account_id, a.type, a.name, a.user_id, a.amount " +
            "FROM accounts a " +
            "INNER JOIN users u ON a.user_id = u.user_id " +
            "WHERE u.email = ?";

    static final String GET_ALL = "SELECT * " +
            "FROM accounts";

    static final String DELETE = "DELETE " +
            "FROM accounts " +
            "WHERE account_id = ?";
}
