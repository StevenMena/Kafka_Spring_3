package com.example.kafka_consumer.listener;

import com.example.kafka_consumer.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListener {

    Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @KafkaListener(topics = "javatechie-topic-3", topicPartitions =
            { @TopicPartition(topic = "javatechie-topic-3", partitions = { "0", "1" },
                    partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0"))
            }
    )
    public void consume1(String message){
        logger.info("Consumer1 consume the message {}", message);
    }

//    @KafkaListener(topics = "javatechie-topic-3", groupId = "it-group")
//    public void consumeEvents(Customer customer){
//        logger.info("Consumer consume the event {}", customer.toString());
//    }

    /*
    @KafkaListener(topics = "javatechie-topic",groupId = "it-group")
    public void consume2(String message){
        logger.info("Consumer2 consume the message {}", message);
    }

    @KafkaListener(topics = "javatechie-topic",groupId = "it-group")
    public void consume3(String message){
        logger.info("Consumer3 consume the message {}", message);
    }

    @KafkaListener(topics = "javatechie-topic",groupId = "it-group")
    public void consume4(String message){
        logger.info("Consumer4 consume the message {}", message);
    }*/

}
