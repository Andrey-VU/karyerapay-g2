package ru.karyeragame.paymentsystem.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.karyeragame.paymentsystem.account.Type;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Поле type не может быть пустым")
    private Type type;

    @NotBlank(message = "Поле name не может быть пустым")
    @Size(min = 2, max = 100, message = "Размер поля name, мин 2, мах 100")
    private String name;

    @NotNull(message = "Поле userId не может быть пустым")
    @PositiveOrZero(message = "Поле userId не может быть отрицательным")
    private Long userId;

    @NotNull(message = "Поле amount не может быть пустым")
    @DecimalMin(value = "0.0", inclusive = true, message = "Поле amount не может быть отрицательным")
    @Digits(integer = 12, fraction = 2, message = "Указано не корректное значение. Более 2х знаков после запятой или слишком большое число.")
    private BigDecimal amount;
}
