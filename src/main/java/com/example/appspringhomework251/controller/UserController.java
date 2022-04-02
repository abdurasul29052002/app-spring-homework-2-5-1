package com.example.appspringhomework251.controller;

import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.UserDto;
import com.example.appspringhomework251.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateUser(@PathVariable UUID id,@RequestBody UserDto userDto){
        Result result = userService.updateUser(id, userDto);
        return ResponseEntity.status(result.isSuccess()?203:409).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id){
        Result result = userService.deleteUser(id);
        return ResponseEntity.status(result.isSuccess()?204:409).body(result);
    }

}
