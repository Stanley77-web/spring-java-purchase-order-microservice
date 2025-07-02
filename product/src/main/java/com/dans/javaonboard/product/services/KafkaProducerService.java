package com.dans.javaonboard.product.services;

import com.dans.javaonboard.product.dtos.ActivationProductOrderDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, ActivationProductOrderDto> kafkaActivationOrder;

    public void publishActivationOrder(ActivationProductOrderDto activationProductOrderDto) {
        String topic = System.getenv("KAFKA_TOPIC_ACTIVATION_ORDER");

        ProducerRecord<String, ActivationProductOrderDto> record =
                new ProducerRecord<>(topic, null, activationProductOrderDto);

        String activationProductDtoClass = "com.dans.javaonboard.ordering.dtos.ActivationProductOrderDto";

        record.headers().add(new RecordHeader("__TypeId__", activationProductDtoClass.getBytes()));

        kafkaActivationOrder.send(record);
    }
}
