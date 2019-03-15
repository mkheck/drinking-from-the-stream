package com.thehecklers.scstsource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class ScstSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScstSourceApplication.class, args);
    }

}

@EnableBinding(Source.class)
@EnableScheduling
@AllArgsConstructor
class Spammer {
    private final Source source;
    private final SubscriberGenerator generator;

    @Scheduled(fixedRate = 1000)
    private void spam() {
        Subscriber sub = generator.generate();
        System.out.println(sub);
        source.output().send(MessageBuilder.withPayload(sub).build());
    }
}

@Component
class SubscriberGenerator {
    private final String[] firstNames = "Alpha, Bravo, Charlie, Delta, Foxtrot, Golf, Hotel, Indigo".split(", ");
    private final String[] lastNames = "Alpha, Bravo, Charlie, Delta, Foxtrot, Golf, Hotel, Indigo".split(", ");
    private final Random rnd = new Random();
    private int i = 0, j = 0;

    Subscriber generate() {
        i = rnd.nextInt(8);
        j = rnd.nextInt(8);

        return new Subscriber(UUID.randomUUID().toString(), firstNames[i], lastNames[j], Instant.now());
    }
}

@Data
@AllArgsConstructor
class Subscriber {
    private final String id, firstName, lastName;
    private final Instant subscribeDate;
}