package com.example.kafka_error_handling.controller;

import com.example.kafka_error_handling.dto.User;
import com.example.kafka_error_handling.publisher.KafkaMessagePublisher;
import com.example.kafka_error_handling.util.CsvReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;

    @PostMapping("/publishNew")
    public ResponseEntity<?> publishEvent(@RequestBody User user){
        try {
            List<User> users = CsvReaderUtils.readDataFromCsv();

            users.forEach(usr-> publisher.sendEvents(usr));

            return ResponseEntity.ok("Message published successfully...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
