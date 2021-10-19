package com.vs4vijay.squidgame.repositories;

import com.vs4vijay.squidgame.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    
}
