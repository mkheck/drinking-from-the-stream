package com.thehecklers.scstprocessor;

import lombok.Value;
import org.apache.tomcat.jni.Proc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.Instant;

@EnableBinding(Processor.class)
@SpringBootApplication
public class ScstProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScstProcessorApplication.class, args);
    }

}

@MessageEndpoint
class Transformer {
    private final Processor processor;

    Transformer(Processor processor) {
        this.processor = processor;
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    Subscriber transform(Subscriber sub) {

        Subscriber newSub = new Subscriber(sub.getId(),
                sub.getFirstName().toUpperCase(),
                sub.getLastName().toUpperCase(),
                sub.getSubscribeDate());

        System.out.println(newSub);

        return newSub;
    }
}

@Value
class Subscriber {
    private final String id, firstName, lastName;
    private final Instant subscribeDate;
}