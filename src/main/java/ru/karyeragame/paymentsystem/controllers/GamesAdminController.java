package ru.karyeragame.paymentsystem.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.karyeragame.paymentsystem.dto.GameCreateDto;
import ru.karyeragame.paymentsystem.dto.GameOutputDto;
import ru.karyeragame.paymentsystem.services.GamesService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/games")
@Slf4j
public class GamesAdminController {

    private final GamesService gamesService;

    @Autowired
    public GamesAdminController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @PostMapping
    GameOutputDto createGame(@RequestBody GameCreateDto gameCreateDto) {
        GameOutputDto response = gamesService.createGame(gameCreateDto);
        log.info(String.format("Time: %s. Method: %s. Income data: %s", LocalDateTime.now(), Thread.currentThread().getStackTrace()[0].getMethodName(), gameCreateDto));
        return response;
    }
}
