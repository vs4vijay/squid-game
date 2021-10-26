package com.vs4vijay.squidgame.models;

import javax.persistence.*;

import com.vs4vijay.squidgame.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity()
@Data()
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
@SQLDelete(sql = "UPDATE game SET is_deleted = true WHERE id = ?")
@Where(clause = BaseModel.SOFT_DELETE_CLAUSE)
@DynamicUpdate
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
    private void onGameCreate() {
        this.round = 1;
        this.status = GameStatus.NOT_STARTED;
        this.isActive = true;
    }
}
