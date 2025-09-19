package com.dans.javaonboard.product.interceptors;

import com.dans.javaonboard.product.dtos.UserDto;
import com.dans.javaonboard.product.services.EncryptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private EncryptionService encryptionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JsonProcessingException {
        String encryptedUser = request.getHeader("x-user");

        if (encryptedUser == null || encryptedUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        String userJson = this.encryptionService.decrypt(encryptedUser);

        if (userJson == null || userJson.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        ObjectMapper mapper = new ObjectMapper();
        UserDto userDto = mapper.readValue(userJson, UserDto.class);

        if (userDto == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        request.setAttribute("user", userDto);

        return true;
    }
}
