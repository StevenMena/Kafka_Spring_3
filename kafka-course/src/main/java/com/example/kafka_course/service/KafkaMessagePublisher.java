package com.example.kafka_course.service;


import com.example.kafka_course.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendMessageToTopic(String message) throws KafkaException {
        try {


            //Send a message event to specific partion
            //CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-topic-3",2,null, message);
            //CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-topic", 2, null, message);
            CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-topic-3", message);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + message + "] " +
                            "with offset=[" + result.getRecordMetadata().offset() + "] " +
                            "in the partion=[" + result.getRecordMetadata().partition() + "]");
                } else {
                    System.out.println("Unable to send the message=[" + message + "] " +
                            "due to: " + ex.getMessage());
                }
            });
        }
        catch (KafkaException e){
            throw new KafkaException("Error when try to publish message " + e.getMessage());
        }
    }

    public void sendEventToTopic(Customer customer){
        CompletableFuture<SendResult<String, Object>> future = template.send("javatechie-topic-2", customer);
        future.whenComplete((result,ex)->{
            if(ex==null){
                System.out.println("Sent message=[" +customer.toString()+"] " +
                        "with offset=["+result.getRecordMetadata().offset()+"] " +
                        "in the partion=[" +result.getRecordMetadata().partition()+"]");
            }
            else{
                System.out.println("Unable to send the message=[" +customer.toString()+"] " +
                        "due to: "+ ex.getMessage());
            }
        });
    }
}
