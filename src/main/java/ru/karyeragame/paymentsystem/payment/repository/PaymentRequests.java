package ru.karyeragame.paymentsystem.payment.repository;

public class PaymentRequests {

    static final String GET_BY_ID = "SELECT * " +
            "FROM payments " +
            "WHERE payments_id = ?";

    static final String GET_BY_EMAIL = "SELECT p.payments_id, p.account_id_from, p.account_id_to, p.amount, p.created_on " +
            "FROM payments p " +
            "INNER JOIN accounts a ON p.account_id_from = a.account_id " +
            "INNER JOIN users u ON a.user_id = u.user_id " +
            "WHERE u.email = ?";

    static final String GET_ALL = "SELECT * " +
            "FROM payments";

    static final String DELETE = "DELETE " +
            "FROM payments " +
            "WHERE payments_id = ?";
}
