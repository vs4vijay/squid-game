package com.vs4vijay.squidgame.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vs4vijay.squidgame.core.SquidGameMapper;
import com.vs4vijay.squidgame.dtos.GameDTO;
import com.vs4vijay.squidgame.errors.ResourceNotFoundException;
import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RequestMapping("/games")
@RestController()
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SquidGameMapper mapper;

    @GetMapping("")
    public List<GameDTO> getAll() {
        List<Game> games = gameService.getAll();
        List<GameDTO> gameDTOs = mapper.toGameDTOs(games);
        return gameDTOs;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public GameDTO create(@Valid @RequestBody GameDTO gameDTO) {
        Game game = mapper.toGame(gameDTO);
        Game createdGame = gameService.create(game);
        GameDTO createdGameDTO = mapper.toGameDTO(createdGame);
        return createdGameDTO;
    }

    @GetMapping("/{id}")
    public GameDTO getById(@PathVariable("id") UUID id) {
        Optional<Game> byId = gameService.getById(id);
        System.out.println("--- byId " + byId);
        if(byId.isPresent()) {
            GameDTO gameDTO = mapper.toGameDTO(byId.get());
            return gameDTO;
        } else {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    @PutMapping("/{id}")
    public GameDTO update(@PathVariable("id") UUID id, @RequestBody GameDTO gameDTO) {
        Game game = mapper.toGame(gameDTO);
        Game updatedGame = gameService.update(id, game);
        GameDTO updatedGameDTO = mapper.toGameDTO(updatedGame);
        return updatedGameDTO;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        gameService.delete(id);
    }
}
