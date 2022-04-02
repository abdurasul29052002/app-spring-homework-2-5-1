package com.example.appspringhomework251.controller;

import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.SalaryDto;
import com.example.appspringhomework251.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @PostMapping
    public HttpEntity<?> addSalary(@RequestBody SalaryDto salaryDto){
        Result result = salaryService.addSalary(salaryDto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @GetMapping("/byuser/{userId}")
    public HttpEntity<?> getSalariesByUser(@PathVariable UUID userId){
        return salaryService.getSalariesByUser(userId);
    }
}
