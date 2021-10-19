package com.vs4vijay.squidgame.core;

import com.vs4vijay.squidgame.dtos.CreateGameDTO;
import com.vs4vijay.squidgame.dtos.GameDTO;
import com.vs4vijay.squidgame.dtos.JoinPlayerDTO;
import com.vs4vijay.squidgame.dtos.PlayerDTO;
import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.models.Player;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SquidGameMapper {
    Game toGame(GameDTO dto);
    Game toGame(CreateGameDTO dto);
    GameDTO toGameDTO(Game game);
    List<GameDTO> toGameDTOs(List<Game> games);

    Player toPlayer(JoinPlayerDTO dto);
    PlayerDTO toPlayerDTO(Player player);
    List<PlayerDTO> toPlayerDTOs(List<Player> players);
}
