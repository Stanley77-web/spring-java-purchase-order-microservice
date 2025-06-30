package com.dans.javaonboard.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishActivationOrder(String productId) {
        String topic = System.getenv("KAFKA_TOPIC_ACTIVATION_ORDER");

        kafkaTemplate.send(topic, productId);
    }
}
