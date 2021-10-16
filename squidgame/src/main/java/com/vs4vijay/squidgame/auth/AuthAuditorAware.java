package com.vs4vijay.squidgame.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuthAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        return Optional.of("Anonymous");
    }
}
