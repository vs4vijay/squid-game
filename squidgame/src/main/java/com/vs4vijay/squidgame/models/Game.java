package com.vs4vijay.squidgame.models;

import javax.persistence.*;

import com.vs4vijay.squidgame.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity()
@Data()
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
public class Game extends BaseModel {
    @Column(nullable = false)
    String name;

    @Column()
    String description;

    @Column()
    Integer round = 1;

    @Column()
    GameStatus status;

    @Column()
    Boolean isActive;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Player> players;

    @PrePersist
    private void onCreate() {
        this.round = 1;
        this.status = GameStatus.NOT_STARTED;
        this.isActive = true;
    }
}
