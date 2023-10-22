package ru.karyeragame.paymentsystem.security.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.karyeragame.paymentsystem.common.validation.Create;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(groups = Create.class, message = "Никнейм пользователя не может быть пустым")
    @Size(groups = {Create.class}, max = 20, message = "Максимальный размер никнейма - 20 символов")
    private String nickname;

    @Size(groups = {Create.class}, max = 30, message = "Максимальный размер email - 30 символов")
    @Email(groups = {Create.class}, message = "Введённый email некорректен")
    @NotBlank(groups = Create.class, message = "Email пользователя не может быть пустым")
    private String email;

    @Size(groups = {Create.class}, max = 30, message = "Максимальный размер пароля - 30 символов")
    @NotBlank(groups = Create.class, message = "Пароль пользователя не может быть пустым")
    private String password;
}
