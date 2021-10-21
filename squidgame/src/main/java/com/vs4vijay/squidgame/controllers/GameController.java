package com.vs4vijay.squidgame.controllers;

import java.util.Map;
import java.util.Optional;

import com.vs4vijay.squidgame.core.SquidGameMapper;
import com.vs4vijay.squidgame.dtos.CreateGameDTO;
import com.vs4vijay.squidgame.dtos.GameDTO;
import com.vs4vijay.squidgame.dtos.ResponseDTO;
import com.vs4vijay.squidgame.errors.ResourceNotFoundException;
import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.services.GameService;
import com.vs4vijay.squidgame.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/games")
@Validated
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SquidGameMapper mapper;

    @Autowired
    private CommandGateway commandGateway;

//    @GetMapping("")
//    public List<GameDTO> getAll(Pageable pageRequest) {
//        List<Game> games = gameService.getAll(pageRequest);
//        List<GameDTO> gameDTOs = mapper.toGameDTOs(games);
//        return gameDTOs;
//    }

    @GetMapping("")
    public ResponseDTO getAll(Pageable pageRequest) {
        Page<Game> gamePage = gameService.getAll(pageRequest);
        Map metadata = CommonUtil.buildMetadataFromPage(gamePage);
        ResponseDTO responseDTO =
                ResponseDTO
                        .builder()
                        .data(gamePage.getContent())
                        .metadata(metadata)
                        .build();
        return responseDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public GameDTO create(@Valid @RequestBody CreateGameDTO gameDTO) {
        Game game = mapper.toGame(gameDTO);
        Game createdGame = gameService.create(game);
        GameDTO createdGameDTO = mapper.toGameDTO(createdGame);
        return createdGameDTO;
    }

    @GetMapping("/{id}")
    public GameDTO getById(@PathVariable("id") String id) {
        Optional<Game> byId = gameService.getById(id);
        if(byId.isPresent()) {
            GameDTO gameDTO = mapper.toGameDTO(byId.get());
            return gameDTO;
        } else {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    @PutMapping("/{id}")
    public GameDTO update(@PathVariable("id") String id, @RequestBody CreateGameDTO gameDTO) {
        // TODO: Check if valid game
        Game game = mapper.toGame(gameDTO);
        // TODO: Partial Update
        Game updatedGame = gameService.update(id, game);
        GameDTO updatedGameDTO = mapper.toGameDTO(updatedGame);
        return updatedGameDTO;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        gameService.delete(id);
    }
}
