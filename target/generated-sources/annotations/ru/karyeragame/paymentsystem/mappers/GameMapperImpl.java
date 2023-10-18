package ru.karyeragame.paymentsystem.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.karyeragame.paymentsystem.dto.GameCreateDto;
import ru.karyeragame.paymentsystem.dto.GameOutputDto;
import ru.karyeragame.paymentsystem.entities.Game;
import ru.karyeragame.paymentsystem.mock.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-17T21:48:37+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public Game gameCreateDtoToGame(GameCreateDto gameCreateDto, User admin) {
        if ( gameCreateDto == null && admin == null ) {
            return null;
        }

        Game game = new Game();

        if ( gameCreateDto != null ) {
            game.setName( gameCreateDto.getName() );
            game.setDescription( gameCreateDto.getDescription() );
        }
        if ( admin != null ) {
            game.setAdmin( admin );
            game.setId( admin.getId() );
        }

        return game;
    }

    @Override
    public GameOutputDto gameToGameOutputDto(Game game) {
        if ( game == null ) {
            return null;
        }

        GameOutputDto gameOutputDto = new GameOutputDto();

        gameOutputDto.setAdminEmail( gameAdminEmail( game ) );
        gameOutputDto.setId( game.getId() );
        gameOutputDto.setName( game.getName() );
        gameOutputDto.setDescription( game.getDescription() );
        gameOutputDto.setAlive( game.isAlive() );
        gameOutputDto.setCreationDate( game.getCreationDate() );

        return gameOutputDto;
    }

    private String gameAdminEmail(Game game) {
        if ( game == null ) {
            return null;
        }
        User admin = game.getAdmin();
        if ( admin == null ) {
            return null;
        }
        String email = admin.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
