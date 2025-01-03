package com.example.kafka_error_handling.consumer;

import com.example.kafka_error_handling.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KafkaMessageConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    //The RetryableTopic abstraction automatically creates retry topics with configurable backoff and error-handling behavior.
    @RetryableTopic(
            // Total number of attempts (including initial try), by default is 3,
            attempts = "4", // It will create N-1 topic, in each retry it create one topic
            backoff = @Backoff(delay = 1000, multiplier = 2.0) // Delay between retries
    )
    @KafkaListener(topics = "${app.topic.name}",groupId = "javatechie-group")
    public void consumeEvents(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset){
        try {
            logger.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(user), topic, offset);
            //validate restricted IP before process the records
            List<String> restrictedIpList = Stream.of("32.241.244.236", "15.55.49.164", "81.1.95.253", "126.130.43.183").collect(Collectors.toList());
            if (restrictedIpList.contains(user.getIpAddress())) {
                throw new RuntimeException("Invalid IP Address received !");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new KafkaException("Error when try to consume message: " + e.getMessage());
        }
    }

    @DltHandler
    public void listenDLT(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        logger.info("DLT Received : {} , from {} , offset {}",user.getFirstName(),topic,offset);
    }
}
