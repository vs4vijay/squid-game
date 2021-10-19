package com.vs4vijay.squidgame.repositories;

import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findPlayersByGame(Game game);
}
