package com.dans.javaonboard.authentication.repositories;

import com.dans.javaonboard.authentication.entities.LoginHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistoryEntity, Integer> {

}
