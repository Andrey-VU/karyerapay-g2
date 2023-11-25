package ru.karyeragame.paymentsystem.payment.repository;

import ru.karyeragame.paymentsystem.payment.model.Payment;

import java.util.List;

public interface PaymentRepository {
    Payment create(Payment payment);

    Payment getById(Long id);

    List<Payment> getAll();

    List<Payment> getAllByEmail(String email);
}
