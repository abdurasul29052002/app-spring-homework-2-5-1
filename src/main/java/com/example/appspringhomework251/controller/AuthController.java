package com.example.appspringhomework251.controller;

import com.example.appspringhomework251.entity.enums.RoleName;
import com.example.appspringhomework251.payload.LoginDto;
import com.example.appspringhomework251.payload.RegisterDto;
import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://192.168.166.88:3000")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register/director")
    public HttpEntity<?> registerDirector(@Valid @RequestBody RegisterDto registerDto){
        Result result = authService.register(registerDto,RoleName.DIRECTOR);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);

    }

    @PostMapping("/register/manager")
    public HttpEntity<?> registerManager(@Valid @RequestBody RegisterDto registerDto){
        Result result = authService.register(registerDto,RoleName.HR_MANAGER);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);

    }

    @PostMapping("/register/user")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto){
        Result result = authService.register(registerDto,RoleName.USER);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        Result result = authService.login(loginDto,httpServletResponse);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/verify")
    public HttpEntity<?> verifyAccount(@RequestParam String emailCode,@RequestParam String email){
        Result result = authService.verifyAccount(emailCode,email);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }
}
