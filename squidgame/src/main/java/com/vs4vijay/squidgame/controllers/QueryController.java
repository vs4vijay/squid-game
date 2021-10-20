package com.vs4vijay.squidgame.controllers;

import com.vs4vijay.squidgame.models.Game;
import com.vs4vijay.squidgame.queries.GetGamesQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/commands/games")
@Validated
@Slf4j
public class QueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("")
    public Object getAllGames() {
        GetGamesQuery getGamesQuery = new GetGamesQuery();

        Object result = queryGateway.query(getGamesQuery,
                ResponseTypes.multipleInstancesOf(Game.class))
                .join();
        System.out.println("result: " + result);
        return result;
    }
}
