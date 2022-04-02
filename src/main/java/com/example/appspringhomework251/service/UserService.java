package com.example.appspringhomework251.service;

import com.example.appspringhomework251.entity.User;
import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.UserDto;
import com.example.appspringhomework251.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthService authService;

    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public HttpEntity<?> getUserById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return ResponseEntity.status(optionalUser.isPresent() ? 200 : 409).body(optionalUser.orElse(null));
    }

    public Result updateUser(UUID id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("User not found", false);
        User user = optionalUser.get();
        user.setName(userDto.getName());
        if (userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
            user.setEnabled(false);
            authService.sendMessage(userDto.getEmail(),"http://localhost:8082/api/auth/verify?emailCode="+user.getEmailCode()+"&email="+user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return new Result("User updated",true);
    }

    public Result deleteUser(UUID id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("User not found",false);
        User user = optionalUser.get();
        user.setEnabled(false);
        return new Result("User deleted",true);
    }
}
