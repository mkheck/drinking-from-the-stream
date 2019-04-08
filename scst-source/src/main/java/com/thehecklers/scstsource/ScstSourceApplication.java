package com.thehecklers.scstsource;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
class CoffeeSender {
    private final Source source;
    private final CoffeeGenerator generator;

    @Scheduled(fixedRate = 1000)
    private void send() {
        WholesaleCoffee coffee = generator.generate();

        System.out.println(coffee);
        source.output().send(MessageBuilder.withPayload(coffee).build());
    }
}

@Component
class CoffeeGenerator {
    private final String[] names = "Kaldi's, Kona, Peruvian, Cereza, Sumatra".split(", ");
    private final Random rnd = new Random();
    private int i = 0;

    WholesaleCoffee generate() {
        i = rnd.nextInt(5);

        return new WholesaleCoffee(UUID.randomUUID().toString(), names[i]);
    }
}

@Data
@AllArgsConstructor
class WholesaleCoffee {
    private final String id, name;
}