package com.example.appspringhomework251.repository;

import com.example.appspringhomework251.entity.Task;
import com.example.appspringhomework251.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailCodeAndEmail(String emailCode, String email);
}
