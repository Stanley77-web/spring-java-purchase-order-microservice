package com.dans.javaonboard.product.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutBoundResponseDto<T> {
    private Integer status;
    private String message;
    private T data;
}
