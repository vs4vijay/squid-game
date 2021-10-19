package com.vs4vijay.squidgame.services;

import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.models.Player;
import com.vs4vijay.squidgame.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service()
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player joinGame(Game game, Player player) {
        // TODO: find a better way to do this
        player.setGame(game);
        playerRepository.save(player);
        return player;
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByGame(Game game) {
        return playerRepository.findPlayersByGame(game);
    }
}
