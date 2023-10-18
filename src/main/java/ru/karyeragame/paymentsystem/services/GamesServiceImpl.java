package ru.karyeragame.paymentsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.karyeragame.paymentsystem.dto.GameCreateDto;
import ru.karyeragame.paymentsystem.dto.GameOutputDto;
import ru.karyeragame.paymentsystem.entities.Game;
import ru.karyeragame.paymentsystem.mappers.GameMapper;
import ru.karyeragame.paymentsystem.mock.User;
import ru.karyeragame.paymentsystem.mock.UserService;
import ru.karyeragame.paymentsystem.repositories.GamesRepository;

import java.time.LocalDateTime;

@Service
public class GamesServiceImpl implements GamesService {
    private final GamesRepository gamesRepository;
    private final UserService userService;
    private final GameMapper gameMapper;

    @Autowired
    public GamesServiceImpl(GamesRepository gamesRepository, UserService userService, GameMapper gameMapper) {
        this.gamesRepository = gamesRepository;
        this.userService = userService;
        this.gameMapper = gameMapper;
    }

    @Override
    public GameOutputDto createGame(GameCreateDto gameCreateDto) {
        User admin = userService.findById(gameCreateDto.getAdminId());
        //caution! add throw exception if admin not found
        Game game = gameMapper.gameCreateDtoToGame(gameCreateDto, admin);
        game.setCreationDate(LocalDateTime.now());
        game = gamesRepository.save(game);
        return gameMapper.gameToGameOutputDto(game);
    }
}