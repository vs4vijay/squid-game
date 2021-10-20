package com.vs4vijay.squidgame.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

//    @Bean
//    public EventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
//        EventStore eventStore = EmbeddedEventStore
//                .builder()
//                .storageEngine(storageEngine)
//                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
//                .build();
//
//        return eventStore;
//    }

    @Bean
    public EventStorageEngine storageEngine() {
        EventStorageEngine storageEngine = new InMemoryEventStorageEngine();
        return storageEngine;
    }
}
