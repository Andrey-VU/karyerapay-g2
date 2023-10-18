package ru.karyeragame.paymentsystem.services;


import ru.karyeragame.paymentsystem.dto.GameCreateDto;
import ru.karyeragame.paymentsystem.dto.GameOutputDto;

public interface GamesService {
    GameOutputDto createGame(GameCreateDto gameCreateDto);
}
