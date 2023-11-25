package ru.karyeragame.paymentsystem.payment.service;

import ru.karyeragame.paymentsystem.payment.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    String payment(PaymentDto paymentDto, String requesterEmail);

    List<PaymentDto> getAll();

    List<PaymentDto> getAllByEmail(String email);
}
