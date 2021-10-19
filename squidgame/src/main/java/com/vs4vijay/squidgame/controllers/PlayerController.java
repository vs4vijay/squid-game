package com.vs4vijay.squidgame.controllers;

import com.vs4vijay.squidgame.core.SquidGameMapper;
import com.vs4vijay.squidgame.dtos.JoinPlayerDTO;
import com.vs4vijay.squidgame.dtos.PlayerDTO;
import com.vs4vijay.squidgame.errors.ResourceNotFoundException;
import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.models.Player;
import com.vs4vijay.squidgame.services.GameService;
import com.vs4vijay.squidgame.services.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/games/{gameId}/players")
@Validated
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SquidGameMapper mapper;

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/join")
    public PlayerDTO joinGame(@PathVariable("gameId") String gameId, @Valid @RequestBody JoinPlayerDTO playerDTO) {
        // TODO: Check for valid gameId
        Optional<Game> gameOptional = gameService.getById(gameId);
        if(gameOptional.isEmpty()) {
            throw new ResourceNotFoundException(gameId.toString());
        }

        Player player = mapper.toPlayer(playerDTO);
        Player joinedPlayer = playerService.joinGame(gameOptional.get(), player);
        PlayerDTO joinedPlayerDTO = mapper.toPlayerDTO(joinedPlayer);

        return joinedPlayerDTO;
    }

    @GetMapping("")
    public List<PlayerDTO> getPlayersByGame(@PathVariable("gameId") String gameId) {
        Optional<Game> gameOptional = gameService.getById(gameId);
        if(gameOptional.isEmpty()) {
            throw new ResourceNotFoundException(gameId.toString());
        }

        List<Player> players = playerService.getPlayersByGame(gameOptional.get());
        List<PlayerDTO> PlayerDTOs = mapper.toPlayerDTOs(players);

        return PlayerDTOs;
    }
}
