package ru.karyeragame.paymentsystem.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karyeragame.paymentsystem.account.model.Account;
import ru.karyeragame.paymentsystem.account.repository.AccountRepository;
import ru.karyeragame.paymentsystem.exceptions.IncorrectData;
import ru.karyeragame.paymentsystem.payment.dto.PaymentDto;
import ru.karyeragame.paymentsystem.payment.mapper.PaymentMapper;
import ru.karyeragame.paymentsystem.payment.model.Payment;
import ru.karyeragame.paymentsystem.payment.repository.PaymentRepository;
import ru.karyeragame.paymentsystem.user.dto.UserFullDto;
import ru.karyeragame.paymentsystem.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public String payment(PaymentDto paymentDto, String requesterEmail) {
        if (paymentDto.getFromAccount().equals(paymentDto.getToAccount())) {
            throw new IncorrectData("Указан одинаковый счёт списания и получения.");
        }
        UserFullDto userFrom = userRepository.getByEmail(requesterEmail);
        Account accountFrom = accountRepository.getById(userFrom.getId());

        if (!accountFrom.getId().equals(paymentDto.getFromAccount())) {
            throw new IncorrectData("Что-то пошло не так. Счёт списания не принадлежит пользователю.");
        }

        if (paymentDto.getAmount().compareTo(accountFrom.getAmount()) > 0) {
            throw new IncorrectData("На вашем счёте не достаточно средств.");
        }

        Account accountTo = accountRepository.getById(paymentDto.getToAccount());
        UserFullDto userTo = userRepository.getById(accountTo.getUserId());

        /* todo реализовать проверку пользователя на блокировку */

        /*
        todo реализовать проверку пользователей на принадлежность к одной игре
        Long gameIdUserFrom = createdGamesRepository.getByUserId(userFrom.getId());
        Long gameIdUserTo = createdGamesRepository.getByUserId(userTo.getId());

        if (!gameIdUserFrom.equals(gameIdUserTo)) {
            throw new IncorrectData("Что-то пошло не так. Получатель в другой Игре.");
        }
        */

        accountRepository.transfer(accountFrom.getId(), accountTo.getId(), paymentDto.getAmount());

        Payment payment = Payment.builder()
                .fromAccount(accountFrom.getId())
                .toAccount(accountTo.getId())
                .amount(paymentDto.getAmount())
                .created(LocalDateTime.now())
                .build();
        paymentRepository.create(payment);

        return "Перевод выполнен.";
    }

    @Override
    public List<PaymentDto> getAll() {
        List<Payment> payments = paymentRepository.getAll();
        return payments.stream()
                .map(PaymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getAllByEmail(String email) {
        List<Payment> payments = paymentRepository.getAllByEmail(email);
        return payments.stream()
                .map(PaymentMapper::toPaymentDto)
                .collect(Collectors.toList());
    }
}
