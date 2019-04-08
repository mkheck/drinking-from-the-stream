package com.thehecklers.scstprocessor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Random;

@SpringBootApplication
public class ScstProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScstProcessorApplication.class, args);
    }

}

@EnableBinding(Processor.class)
@MessageEndpoint
class CoffeeTransformer {
    private final Random rnd = new Random();

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    RetailCoffee transform(WholesaleCoffee wCoffee) {

        RetailCoffee rCoffee = new RetailCoffee(wCoffee.getId(),
                wCoffee.getName(),
                rnd.nextInt(2) == 0 ? RetailCoffee.CoffeeState.WHOLE_BEAN : RetailCoffee.CoffeeState.GROUND);

        System.out.println(rCoffee);

        return rCoffee;
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

@Data
@AllArgsConstructor
class WholesaleCoffee {
    private final String id, name;
}