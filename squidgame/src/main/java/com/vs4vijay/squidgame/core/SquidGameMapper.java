package com.vs4vijay.squidgame.core;

import com.vs4vijay.squidgame.dtos.CreateGameDTO;
import com.vs4vijay.squidgame.dtos.GameDTO;
import com.vs4vijay.squidgame.models.Game;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SquidGameMapper {
    Game toGame(GameDTO dto);
    Game toGame(CreateGameDTO dto);

    GameDTO toGameDTO(Game game);

    List<GameDTO> toGameDTOs(List<Game> games);
}
