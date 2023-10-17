package ru.karyeragame.paymentsystem.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.karyeragame.paymentsystem.common.validation.Create;
import ru.karyeragame.paymentsystem.common.validation.Update;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(groups = Create.class, message = "Никнейм пользователя не может быть пустым")
    @Size(groups = {Create.class, Update.class}, max = 20, message = "Максимальный размер никнейма - 20 символов")
    private String nickname;

    @Size(groups = {Create.class, Update.class}, max = 30, message = "Максимальный размер email - 30 символов")
    @Email(groups = {Create.class, Update.class}, message = "Введённый email некорректен")
    @NotBlank(groups = Create.class, message = "Email пользователя не может быть пустым")
    private String email;

    @Size(groups = {Create.class, Update.class}, max = 30, message = "Максимальный размер пароля - 30 символов")
    @NotBlank(groups = Create.class, message = "Пароль пользователя не может быть пустым")
    private String password;
}
