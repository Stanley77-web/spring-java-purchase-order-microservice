package com.dans.javaonboard.ordering.services;

import com.dans.javaonboard.ordering.dtos.ActivateProductDto;
import com.dans.javaonboard.ordering.dtos.ActivationProductOrderDto;
import com.dans.javaonboard.ordering.dtos.OutBoundResponseDto;
import com.dans.javaonboard.ordering.entities.TransactionHistoriesEntity;
import com.dans.javaonboard.ordering.repositories.TransactionHistoriesRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderingService {

    private final RestTemplate restTemplateOutBound;
    private final TransactionHistoriesRepository transactionHistoriesRepository;

    public OrderingService(RestTemplate restTemplateOutBound, TransactionHistoriesRepository transactionHistoriesRepository) {
        this.restTemplateOutBound = restTemplateOutBound;
        this.transactionHistoriesRepository = transactionHistoriesRepository;
    }

    @KafkaListener(
            topics = "${KAFKA_TOPIC_ACTIVATION_ORDER:activation-order}",
            groupId = "${KAFKA_GROUP_ID:activation-order-group}"
    )
    public void ActivationProductOrderProcess(ActivationProductOrderDto activationProductOrderDto) throws BadRequestException {
        String productId = activationProductOrderDto.getProductId();
        String username = activationProductOrderDto.getUsername();

        String transactionId = generateTransactionId(
                productId,
                username
        );

        ActivateProductDto activateProductDto = new ActivateProductDto(
                productId,
                transactionId
        );

        RequestEntity<ActivateProductDto> requestEntity = RequestEntity
                .post("/activate/product")
                .contentType(MediaType.APPLICATION_JSON)
                .body(activateProductDto);
        ParameterizedTypeReference<
                OutBoundResponseDto<Void>
                > responseType = new ParameterizedTypeReference<>() {};


        OutBoundResponseDto<Void> response =
                restTemplateOutBound.exchange(
                        requestEntity,
                        responseType
                ).getBody();

        if (response == null) {
            throw new RuntimeException("Failed to activate product with ID: " + productId);
        }

        if (response.getStatus() != 0) {
            throw new BadRequestException(
                    "Failed to activate product with ID: " + productId +
                    ", status: " + response.getStatus() +
                    ", message: " + response.getMessage()
            );
        }

        TransactionHistoriesEntity transactionHistoriesEntity = new TransactionHistoriesEntity(
                transactionId,
                productId,
                LocalDateTime.now(),
                "system"
        );

        transactionHistoriesRepository.save(transactionHistoriesEntity);
    }

    public List<TransactionHistoriesEntity> getTransactionHistories(String username) {
        return transactionHistoriesRepository.findByCreateBy(username);
    }

    private String generateTransactionId(String productId, String username) {
        return "TRX-" + productId + "-" + username + "-" + System.currentTimeMillis();
    }
}
