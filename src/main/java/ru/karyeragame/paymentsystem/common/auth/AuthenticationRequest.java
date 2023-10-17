package ru.karyeragame.paymentsystem.common.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.karyeragame.paymentsystem.common.validation.AuthenticationValid;
import ru.karyeragame.paymentsystem.common.validation.Create;
import ru.karyeragame.paymentsystem.common.validation.Update;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Size(groups = {AuthenticationValid.class}, max = 30, message = "Максимальный размер email - 30 символов")
    @Email(groups = {AuthenticationValid.class}, message = "Введённый email некорректен")
    @NotBlank(groups = AuthenticationValid.class, message = "Email пользователя не может быть пустым")
    private String email;

    @Size(groups = {AuthenticationValid.class}, max = 30, message = "Максимальный размер пароля - 30 символов")
    @NotBlank(groups = AuthenticationValid.class, message = "Пароль пользователя не может быть пустым")
    private String password;
}
