package com.example.kafka_error_handling;

import org.springframework.boot.SpringApplication;

public class TestKafkaErrorHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkaErrorHandlingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
