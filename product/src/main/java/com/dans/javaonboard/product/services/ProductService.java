package com.dans.javaonboard.product.services;

import com.dans.javaonboard.product.dtos.OutBoundResponseDto;
import com.dans.javaonboard.product.dtos.ProductDataDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private final RestTemplate restTemplateOutBound;
    private final KafkaProducerService kafkaProducerService;

    public ProductService(RestTemplate restTemplateOutBound, KafkaProducerService kafkaProducerService) {
        this.restTemplateOutBound = restTemplateOutBound;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Cacheable("outbound:products")
    public ProductDataDto getAllProduct() {
        RequestEntity<Void> requestEntity = RequestEntity
                .get("/product ")
                .build();
        ParameterizedTypeReference<
                OutBoundResponseDto<
                                        ProductDataDto
                                        >
                > responseType = new ParameterizedTypeReference<>() {};

        OutBoundResponseDto<ProductDataDto> response =
                restTemplateOutBound.exchange(
                        requestEntity,
                        responseType
                ).getBody();

        if (response == null || response.getData() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product data not found");
        }

        return response.getData();
    }

    public String activateProduct(String productId) {
        ProductDataDto productData = this.getAllProduct();

        for (ProductDataDto.ProductDto product : productData.getProducts()) {
            if (product.getProductId().equals(productId)) {

                kafkaProducerService.publishActivationOrder(productId);

                return "Activation product with ID: " + productId + " has being processed successfully";
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with ID: " + productId + " is not valid");
    }
}
