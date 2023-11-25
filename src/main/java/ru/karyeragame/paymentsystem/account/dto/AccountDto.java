package ru.karyeragame.paymentsystem.account.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.karyeragame.paymentsystem.account.Type;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotBlank(message = "Поле name не может быть пустым")
    @Size(min = 2, max = 100, message = "Размер поля name, мин 2, мах 100")
    private String name;

    @NotNull(message = "Поле userId не может быть пустым")
    @PositiveOrZero(message = "Поле userId не может быть отрицательным")
    private Long userId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Поле amount не может быть отрицательным")
    @Digits(integer = 12, fraction = 2, message = "Указано не корректное значение. Более 2х знаков после запятой или слишком большое число.")
    private BigDecimal amount;
}
