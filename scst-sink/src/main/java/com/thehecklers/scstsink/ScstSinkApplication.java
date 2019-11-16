package com.thehecklers.scstsink;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessageEndpoint;

import java.util.function.Consumer;

@SpringBootApplication
public class ScstSinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScstSinkApplication.class, args);
    }

}

/*  Legacy Spring Cloud Stream API
@EnableBinding(Sink.class)
@MessageEndpoint
class CoffeeDrinker {

    @StreamListener(value = Sink.INPUT)
    private void drink(RetailCoffee coffee) {
        System.out.println(coffee);
    }

}
*/

@Configuration
class CoffeeDrinker {
    @Bean
    Consumer<RetailCoffee> drinkIt() {
        return System.out::println;
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