package com.dans.javaonboard.ordering.dtos;

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
