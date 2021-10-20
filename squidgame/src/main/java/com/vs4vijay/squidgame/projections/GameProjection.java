package com.vs4vijay.squidgame.projections;

import com.vs4vijay.squidgame.events.GameCreatedEvent;
import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.queries.GetGamesQuery;
import com.vs4vijay.squidgame.repositories.GameRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameProjection {

    @Autowired
    private GameRepository gameRepository;

    @EventHandler
    public void on(GameCreatedEvent event) {
        Game game = Game
                .builder()
                .name(event.getName())
                .description(event.getDescription())
                .round(event.getRound())
                .status(event.getStatus())
                .build();
        game.setId(event.getGameId());

        gameRepository.save(game);
    }

    @QueryHandler
    public List<Game> handle(GetGamesQuery query) {
        List<Game> games = gameRepository.findAll();
        return games;
    }
}
