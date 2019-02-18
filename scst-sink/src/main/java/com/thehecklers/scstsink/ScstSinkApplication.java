package com.thehecklers.scstsink;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;

import java.time.Instant;

@EnableBinding(Sink.class)
@SpringBootApplication
public class ScstSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScstSinkApplication.class, args);
	}

}

@MessageEndpoint
class Catcher {

	@StreamListener(Sink.INPUT)
	void logMessage(Subscriber sub) {
		System.out.println(sub);
	}

//	@StreamListener(Sink.INPUT)
//	void anotherRoute(Subscriber subscriber) {
//		// doSomethingElse();
//	}
}

@Value
class Subscriber {
	private final String id, firstName, lastName;
	private final Instant subscribeDate;
}