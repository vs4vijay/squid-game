package com.vs4vijay.squidgame.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController()
public class GameController {

    @Autowired
    private GameService gameService;
    
    @PostMapping("/games")
    public Game create(@RequestBody Game game) {
        Game createdGame = gameService.create(game);
        return createdGame;
    }

    @GetMapping("/games/{id}")
    public Game getById(@PathVariable("id") UUID id) {
        Optional<Game> byId = gameService.getById(id);
        System.out.println("--- byId " + byId);
        if(byId.isPresent()) {
            return byId.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }

    @GetMapping("/games")
    public List<Game> getAll() {
        List<Game> games = gameService.getAll();
        return games;
    }
}
