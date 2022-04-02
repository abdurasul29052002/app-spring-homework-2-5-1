package com.example.appspringhomework251.repository;

import com.example.appspringhomework251.entity.Turnstile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurnstileRepository extends JpaRepository<Turnstile, UUID> {
    Optional<Turnstile> findByUserIdAndCompleted(UUID user_id, boolean completed);

    @Query(nativeQuery = true,value = "select * from turnstile where user_id=:id and income between :need_time and :current_time")
    List<Turnstile> findAllUsers(UUID id, Timestamp need_time,Timestamp current_time);
}
