package com.dans.javaonboard.authentication.services;

import com.dans.javaonboard.authentication.dtos.LoginDto;
import com.dans.javaonboard.authentication.dtos.UserDto;
import com.dans.javaonboard.authentication.dtos.VerifyTokenDto;
import com.dans.javaonboard.authentication.entities.LoginHistoryEntity;
import com.dans.javaonboard.authentication.entities.UserEntity;
import com.dans.javaonboard.authentication.repositories.LoginHistoryRepository;
import com.dans.javaonboard.authentication.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private HashService hashService;

    public LoginDto login(UserEntity user){
        LoginHistoryEntity loginHistory = new LoginHistoryEntity();

        String username = user.getUsername();
        String password = encryptionService.decrypt(user.getPassword());

        UserEntity userData = userRepository.findByUsername(username)
                .orElseThrow(() -> {

                    loginHistory.setSuccessful(false);
                    loginHistory.setLoginTime(LocalDateTime.now());
                    loginHistory.setLoginLog("Failed login attempt for username: " + username);

                    loginHistoryRepository.save(loginHistory);


                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
                });

        if (!hashService.matchPassword(password, userData.getPassword())) {

            loginHistory.setSuccessful(false);
            loginHistory.setLoginTime(LocalDateTime.now());
            loginHistory.setLoginLog("Failed login attempt for username: " + username);

            loginHistoryRepository.save(loginHistory);

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }


        loginHistory.setUser(userData);
        loginHistory.setLoginTime(LocalDateTime.now());
        loginHistory.setSuccessful(true);
        loginHistory.setLoginLog("User " + userData.getUsername() + " logged in successfully");

        loginHistoryRepository.save(loginHistory);

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(jwtService.createToken(userData.getUsername()));

        return loginDto;
    }

    public VerifyTokenDto verifyToken(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");

        request.setAttribute("claims", null);

        return new VerifyTokenDto(
                "success",
                new UserDto(
                        (String) claims.get("username")
                )
        );
    }
}
