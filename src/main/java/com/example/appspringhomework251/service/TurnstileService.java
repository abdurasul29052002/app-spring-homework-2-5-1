package com.example.appspringhomework251.service;

import com.example.appspringhomework251.entity.Turnstile;
import com.example.appspringhomework251.entity.User;
import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.TurnstileDto;
import com.example.appspringhomework251.repository.TurnstileRepository;
import com.example.appspringhomework251.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TurnstileService {

    @Autowired
    TurnstileRepository turnstileRepository;
    @Autowired
    UserRepository userRepository;

    public Result addTurnstile(TurnstileDto turnstileDto) {
        Turnstile turnstile = new Turnstile();
        turnstile.setIncome(turnstileDto.getIncome());
        Optional<User> optionalUser = userRepository.findById(turnstileDto.getUserId());
        if (!optionalUser.isPresent())
            return new Result("User not found",false);
        User user = optionalUser.get();
        turnstile.setUser(user);
        turnstile.setCompleted(false);
        turnstileRepository.save(turnstile);
        return new Result("Turnstile saved",true);
    }

    public HttpEntity<?> getAllTurnstiles(){
        return ResponseEntity.ok(turnstileRepository.findAll());
    }

    public HttpEntity<?> getTurnstile(UUID id){
        Optional<Turnstile> optionalTurnstile = turnstileRepository.findById(id);
        return ResponseEntity.status(optionalTurnstile.isPresent()?200:409).body(optionalTurnstile.orElse(null));
    }

    public HttpEntity<?> getTurnstileByUserIdAndTime(UUID id, Timestamp needTime){
        List<Turnstile> allUsers = turnstileRepository.findAllUsers(id, needTime, new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(allUsers);
    }

    public Result updateTurnstile(UUID userId, TurnstileDto turnstileDto){
        if (turnstileDto.getOutcome()==null)
            return new Result("Error check the form",false);
        Optional<Turnstile> optionalTurnstile = turnstileRepository.findByUserIdAndCompleted(userId,false);
        if (!optionalTurnstile.isPresent())
            return new Result("Turnstile not found",false);
        Turnstile turnstile = optionalTurnstile.get();
        turnstile.setOutcome(turnstileDto.getOutcome());
        turnstile.setCompleted(true);
        turnstileRepository.save(turnstile);
        return new Result("Turnstile updated",true);
    }

    public Result deleteTurnstile(UUID id){
        try {
            turnstileRepository.deleteById(id);
            return new Result("Turnstile deleted",true);
        }catch (Exception e){
            return new Result("Something went terribly wrong. ",false);
        }
    }
}
