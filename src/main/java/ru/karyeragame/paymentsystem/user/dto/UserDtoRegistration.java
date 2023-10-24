package ru.karyeragame.paymentsystem.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRegistration {
    //private String avatar;
    @NotBlank(message = "Не заполнено поле")
    @Size(min = 2, max = 250)
    private String name;
    @NotBlank(message = "Не заполнено поле")
    @Email(message = "Некорректный емайл")
    @Size(min = 6, max = 254)
    private String email;
    @NotBlank(message = "Не заполнено поле")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    private String passwordConfirm;
}
