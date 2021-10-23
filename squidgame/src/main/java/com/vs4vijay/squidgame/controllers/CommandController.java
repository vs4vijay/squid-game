package com.vs4vijay.squidgame.controllers;

import com.vs4vijay.squidgame.commands.CreateGameCommand;
import com.vs4vijay.squidgame.dtos.CreateGameDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController()
@RequestMapping("/commands")
@Validated
@Slf4j
public class CommandController {

    @Autowired
    private CommandGateway commandGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("createGame")
    public Object createGame(@Valid @RequestBody CreateGameDTO gameDTO) {
        CreateGameCommand createGameCommand = CreateGameCommand.builder()
                .gameId(UUID.randomUUID().toString())
                .name(gameDTO.getName())
                .build();

        Object result = commandGateway.sendAndWait(createGameCommand);
        System.out.println("result: " + result);
        return Map.of("id", result);
    }
}
