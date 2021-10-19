package com.vs4vijay.squidgame.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
public class Player extends BaseModel {
    @Column()
    String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    Game game;
}
