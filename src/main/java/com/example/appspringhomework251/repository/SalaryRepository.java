package com.example.appspringhomework251.repository;

import com.example.appspringhomework251.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalaryRepository extends JpaRepository<Salary,Integer> {
    List<Salary> findAllByUserId(UUID user_id);
}
