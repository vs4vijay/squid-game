package com.vs4vijay.squidgame.aggregates;

import com.vs4vijay.squidgame.commands.CreateGameCommand;
import com.vs4vijay.squidgame.commands.JoinGameCommand;
import com.vs4vijay.squidgame.enums.GameStatus;
import com.vs4vijay.squidgame.events.GameCreatedEvent;
import com.vs4vijay.squidgame.events.PlayerJoinedEvent;
import com.vs4vijay.squidgame.models.Player;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Data
public class GameAggregate {

    @AggregateIdentifier
    public String gameId;

    public Integer round;

    public GameStatus status;

    public List<Player> players;

    public GameAggregate() {
    }

    @CommandHandler
    public GameAggregate(CreateGameCommand cmd) {
        GameCreatedEvent gameCreatedEvent =
                GameCreatedEvent
                        .builder()
                        .gameId(cmd.getGameId())
                        .name(cmd.getName())
                        .build();

        AggregateLifecycle.apply(gameCreatedEvent);
    }

    @CommandHandler
    public void on(JoinGameCommand cmd) {
        // TODO: validations: game should not started
        if (!GameStatus.NOT_STARTED.equals(status)) {
            throw new IllegalStateException("Game is already started");
        }

        AggregateLifecycle.apply(new PlayerJoinedEvent(cmd.gameId, cmd.playerName));
    }

    @EventSourcingHandler
    public void on(GameCreatedEvent event) {
        this.gameId = event.getGameId();
        this.round = 1;
        this.status = GameStatus.NOT_STARTED;
        this.players = new ArrayList<>();
    }

    @EventSourcingHandler
    public void on(PlayerJoinedEvent event) {
        this.players.add(new Player(event.playerName));
    }
}
