package com.dans.javaonboard.authentication.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_histories")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "is_successful", nullable = false)
    private boolean isSuccessful;

    @Column(name = "login_log", nullable = false)
    private String loginLog;

    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
