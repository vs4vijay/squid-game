package com.vs4vijay.squidgame.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
