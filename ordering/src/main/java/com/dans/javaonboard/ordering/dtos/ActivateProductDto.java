package com.dans.javaonboard.ordering.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivateProductDto {
    private String productId;
    private String transactionId;
}
