package com.dans.javaonboard.ordering.repositories;

import com.dans.javaonboard.ordering.entities.TransactionHistoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoriesRepository extends JpaRepository<TransactionHistoriesEntity, String> {
    List<TransactionHistoriesEntity> findByCreateBy(String createBy);
}
