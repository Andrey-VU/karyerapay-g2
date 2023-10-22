package ru.karyeragame.paymentsystem.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.karyeragame.paymentsystem.security.authentication.dto.AuthenticationRequest;
import ru.karyeragame.paymentsystem.security.authentication.dto.AuthenticationResponse;
import ru.karyeragame.paymentsystem.security.authentication.dto.RegisterRequest;
import ru.karyeragame.paymentsystem.security.config.JwtService;
import ru.karyeragame.paymentsystem.user.Role;
import ru.karyeragame.paymentsystem.user.dto.UserFullDto;
import ru.karyeragame.paymentsystem.user.repository.UserDbRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDbRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Добавить отправитель электронных писем
     * для отправки приветственного письма на почту пользователя при регистрации
     */

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserFullDto.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.create(user);
        var jwtToken = jwtService.generateToken(user);
        /**
         * Добавляем сюда отправку письма
         */
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.getByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
