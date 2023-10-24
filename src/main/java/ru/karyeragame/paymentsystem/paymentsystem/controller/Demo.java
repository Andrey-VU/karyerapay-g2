package ru.karyeragame.paymentsystem.paymentsystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/karyerapay/paymentsystem")
@AllArgsConstructor
public class Demo {

    @GetMapping
    @RequestMapping("/demo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> game() {
        return ResponseEntity.ok("/karyerapay/paymentsystem/demo доступен USER, Admin");
    }
}
