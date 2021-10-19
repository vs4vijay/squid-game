package com.vs4vijay.squidgame.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinGameCommand {
    @TargetAggregateIdentifier
    public String gameId;

    public String playerName;
}
