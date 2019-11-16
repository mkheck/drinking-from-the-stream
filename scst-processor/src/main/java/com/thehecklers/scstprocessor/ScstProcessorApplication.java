package com.thehecklers.scstprocessor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Random;
import java.util.function.Function;

@SpringBootApplication
public class ScstProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScstProcessorApplication.class, args);
    }

}

/*  Legacy Spring Cloud Stream API
@EnableBinding(Processor.class)
@MessageEndpoint
class CoffeeTransformer {
    private final Random rnd = new Random();

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    RetailCoffee transform(WholesaleCoffee wCoffee) {

        RetailCoffee rCoffee = new RetailCoffee(wCoffee.getId(),
                wCoffee.getName(),
                rnd.nextInt(2) == 0
                        ? RetailCoffee.State.WHOLE_BEAN
                        : RetailCoffee.State.GROUND);

        System.out.println(rCoffee);

        return rCoffee;
    }
}
*/

@Configuration
class CoffeeRoaster {
    private final Random rnd = new Random();

    @Bean
    Function<WholesaleCoffee, RetailCoffee> processIt() {
        return wCoffee -> {
            RetailCoffee rCoffee = new RetailCoffee(wCoffee.getId(),
                    wCoffee.getName(),
                    rnd.nextInt(2) == 0
                            ? RetailCoffee.State.WHOLE_BEAN
                            : RetailCoffee.State.GROUND);

            System.out.println(rCoffee);
            return rCoffee;
        };
    }

    @Bean
    Function<RetailCoffee, RetailCoffee> fixIt() {
        return coffee -> new RetailCoffee(coffee.getId(),
                coffee.getName(),
                RetailCoffee.State.WHOLE_BEAN);
    }
}

@Data
@AllArgsConstructor
class RetailCoffee {
    enum State {
        WHOLE_BEAN,
        GROUND
    }

    private String id, name;
    private State state;
}

@Data
@AllArgsConstructor
class WholesaleCoffee {
    private final String id, name;
}