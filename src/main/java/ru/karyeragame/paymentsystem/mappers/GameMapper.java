package ru.karyeragame.paymentsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.karyeragame.paymentsystem.dto.GameCreateDto;
import ru.karyeragame.paymentsystem.dto.GameOutputDto;
import ru.karyeragame.paymentsystem.entities.Game;
import ru.karyeragame.paymentsystem.mock.User;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface GameMapper {

    @Mapping(target = "admin", source = "admin")
    Game gameCreateDtoToGame(GameCreateDto gameCreateDto, User admin);

    @Mapping(target = "adminEmail", source = "admin.email")
    GameOutputDto gameToGameOutputDto(Game game);
}
