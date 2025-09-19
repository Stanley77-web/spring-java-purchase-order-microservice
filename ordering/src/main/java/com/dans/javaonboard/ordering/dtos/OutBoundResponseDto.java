package com.dans.javaonboard.ordering.dtos;

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
