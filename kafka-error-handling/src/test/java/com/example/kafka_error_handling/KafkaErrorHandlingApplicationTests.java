package com.example.kafka_error_handling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class KafkaErrorHandlingApplicationTests {

	@Test
	void contextLoads() {
	}

}
