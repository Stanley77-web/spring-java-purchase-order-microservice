package com.dans.javaonboard.authentication.repositories;

import com.dans.javaonboard.authentication.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
    Optional<UserEntity> login(String username, String password);
}
