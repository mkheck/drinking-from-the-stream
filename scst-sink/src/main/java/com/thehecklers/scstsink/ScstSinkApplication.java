package com.thehecklers.scstsink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;

import java.time.Instant;

@SpringBootApplication
public class ScstSinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScstSinkApplication.class, args);
    }

}

@EnableBinding(Sink.class)
@MessageEndpoint
class CoffeeDrinker {

    @StreamListener(Sink.INPUT)
    private void drink(RetailCoffee coffee) {
        System.out.println(coffee);
    }

}

@Data
@AllArgsConstructor
class RetailCoffee {
    enum CoffeeState {
        WHOLE_BEAN,
        GROUND
    }

    private String id, name;
    private CoffeeState state;
}