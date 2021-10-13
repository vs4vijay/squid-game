package com.vs4vijay.squidgame.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/games")
@RestController()
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("")
    public List<Game> getAll() {
        List<Game> games = gameService.getAll();
        return games;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Game create(@RequestBody Game game) {
        Game createdGame = gameService.create(game);
        return createdGame;
    }

    @GetMapping("/{id}")
    public Game getById(@PathVariable("id") UUID id) {
        Optional<Game> byId = gameService.getById(id);
        System.out.println("--- byId " + byId);
        if(byId.isPresent()) {
            return byId.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    @PutMapping("/{id}")
    public Game update(@PathVariable("id") UUID id, @RequestBody Game game) {
        Game updatedGame = gameService.update(id, game);
        return updatedGame;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        gameService.delete(id);
    }
}
