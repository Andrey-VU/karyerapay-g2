package ru.karyeragame.paymentsystem.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.karyeragame.paymentsystem.common.validation.Create;
import ru.karyeragame.paymentsystem.common.validation.Update;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
