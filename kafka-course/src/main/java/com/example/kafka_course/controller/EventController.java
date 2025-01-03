package com.example.kafka_course.controller;

import com.example.kafka_course.dto.Customer;
import com.example.kafka_course.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher publisher;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message){
        try {
            for(int i=0;i<=1000;i++) {
                publisher.sendMessageToTopic(message+" partionv2: "+ i);
            }

            return ResponseEntity.ok("Message published successfully..");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publishMessage(@RequestBody Customer customer){
        try {
            publisher.sendEventToTopic(customer);
            return ResponseEntity.ok("Message published successfully..");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
