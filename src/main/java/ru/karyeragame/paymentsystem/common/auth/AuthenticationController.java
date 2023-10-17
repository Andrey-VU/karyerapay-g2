package ru.karyeragame.paymentsystem.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.karyeragame.paymentsystem.common.validation.AuthenticationValid;
import ru.karyeragame.paymentsystem.common.validation.Create;

@Controller
@RequestMapping("/karyera-game/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody @Validated(Create.class) RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Validated(AuthenticationValid.class) AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
