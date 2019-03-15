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
class Catcher {

    @StreamListener(Sink.INPUT)
    void logMessage(Subscriber sub) {
        System.out.println(sub);
    }

}

@Data
@AllArgsConstructor
class Subscriber {
    private final String id, firstName, lastName;
    private final Instant subscribeDate;
}