package com.vs4vijay.squidgame.events;

import com.vs4vijay.squidgame.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCreatedEvent {
    public String gameId;

    public String name;

    public String description;

    public Integer round;

    public GameStatus status;
}
