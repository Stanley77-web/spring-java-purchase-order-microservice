package com.dans.javaonboard.product.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDataDto implements Serializable {
    private List<ProductDto> products;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductDto implements Serializable {
        private String productId;
        private String productName;
        private Integer price;
        private List<QuotaDto> quota;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class QuotaDto implements Serializable {
        private String quotaId;
        private String quotaName;
        private String value;
    }
}
