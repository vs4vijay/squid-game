package com.vs4vijay.squidgame.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerJoinedEvent {
    public String gameId;

    public String playerName;
}
