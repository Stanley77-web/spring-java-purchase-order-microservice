package com.dans.javaonboard.ordering.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_histories")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoriesEntity {
    @Id
    private String id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "create_by", nullable = false)
    private String createBy;
}
