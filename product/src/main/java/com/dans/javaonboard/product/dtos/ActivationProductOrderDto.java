package com.dans.javaonboard.product.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActivationProductOrderDto {
    private String productId;
    private String username;
}
