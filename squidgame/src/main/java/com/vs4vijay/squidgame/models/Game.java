package com.vs4vijay.squidgame.models;

import javax.persistence.*;

import com.vs4vijay.squidgame.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity()
@Data()
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

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    List<Player> players;

    @PrePersist
    private void onCreate() {
        this.round = 1;
        this.status = GameStatus.NOT_STARTED;
        System.out.println("onCreate--" + this);
    }
}
