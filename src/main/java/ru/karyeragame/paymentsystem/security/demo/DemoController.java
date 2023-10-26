package ru.karyeragame.paymentsystem.security.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/karyera-game/demo-auth")
public class DemoController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Привет от защищённого эндпоинта! :)");
    }
}
