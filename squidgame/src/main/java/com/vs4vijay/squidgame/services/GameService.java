package com.vs4vijay.squidgame.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    
    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> getById(UUID id) {
        return gameRepository.findById(id);
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }
}
