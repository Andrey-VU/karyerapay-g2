package ru.karyeragame.paymentsystem.payment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.karyeragame.paymentsystem.payment.dto.PaymentDto;
import ru.karyeragame.paymentsystem.payment.service.PaymentService;
import ru.karyeragame.paymentsystem.security.config.JwtService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String payment(@RequestBody @Valid PaymentDto paymentDto,
                          @RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String requesterEmail = jwtService.extractUsername(jwt);
        return paymentService.payment(paymentDto, requesterEmail);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentDto> getAll() {
        return paymentService.getAll();
    }

    @GetMapping(("/email/{email}"))
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentDto> getAllByEmail(@PathVariable @Email String email) {
        return paymentService.getAllByEmail(email);
    }
}
