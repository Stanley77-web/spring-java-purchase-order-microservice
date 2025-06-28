package com.dans.javaonboard.authentication.controllers;

import com.dans.javaonboard.authentication.entities.UserEntity;
import com.dans.javaonboard.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Iterable<UserEntity>> getAllUsers() {
        Iterable<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Integer id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Integer id, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        String response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

}
