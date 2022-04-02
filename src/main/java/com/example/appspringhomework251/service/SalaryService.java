package com.example.appspringhomework251.service;

import com.example.appspringhomework251.entity.Salary;
import com.example.appspringhomework251.entity.User;
import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.SalaryDto;
import com.example.appspringhomework251.repository.SalaryRepository;
import com.example.appspringhomework251.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SalaryService {

    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    UserRepository userRepository;

    public Result addSalary(SalaryDto salaryDto){
        Optional<User> optionalUser = userRepository.findById(salaryDto.getUserId());
        if (!optionalUser.isPresent())
            return new Result("User not found",false);
        User user = optionalUser.get();

        Salary salary = new Salary();
        salary.setAmount(salaryDto.getAmount());
        salary.setUser(user);
        salaryRepository.save(salary);
        return new Result("Salary added",true);
    }

    public HttpEntity<?> getSalariesByUser(UUID userId) {
        return ResponseEntity.ok(salaryRepository.findAllByUserId(userId));
    }
}
