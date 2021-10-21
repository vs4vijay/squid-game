package com.vs4vijay.squidgame.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vs4vijay.squidgame.errors.ResourceNotFoundException;
import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.repositories.GameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@Service()
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    
    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> getById(String id) {
        return gameRepository.findById(id);
    }

//    public List<Game> getAll() {
//        return gameRepository.findAll();
//    }

    public Page<Game> getAll(Pageable pageRequest) {
        return gameRepository.findAll(pageRequest);
    }

    public Game update(String id, Game game) {
        gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        game.setId(id);
        return gameRepository.save(game);
    }

    public Game partialUpdate(String id, Game game) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if(gameOptional.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        game.setId(id);
//        BeanUtils.copyProperties();
        return gameRepository.save(game);
    }

    public void delete(String id) {
        gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        gameRepository.deleteById(id);
    }
}
