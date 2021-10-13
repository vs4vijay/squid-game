package com.vs4vijay.squidgame;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GamePlatformRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.err.println("GamePlatformRunner Running");
    }

}