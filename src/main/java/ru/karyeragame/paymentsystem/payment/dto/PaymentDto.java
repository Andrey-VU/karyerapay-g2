package ru.karyeragame.paymentsystem.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long id;
    @NotNull(message = "Поле fromAccount не может быть пустым")
    @Positive
    private Long fromAccount;
    @NotNull(message = "Поле toAccount не может быть пустым")
    @Positive
    private Long toAccount;
    @NotNull(message = "Поле amount не может быть пустым")
    @DecimalMin(value = "0.0", inclusive = false, message = "Поле amount не может быть отрицательным")
    @Digits(integer = 12, fraction = 2, message = "Указано не корректное значение. Более 2х знаков после запятой или слишком большое число.")
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

}
