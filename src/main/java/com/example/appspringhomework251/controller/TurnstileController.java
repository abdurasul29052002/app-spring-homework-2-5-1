package com.example.appspringhomework251.controller;

import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.TurnstileDto;
import com.example.appspringhomework251.service.TurnstileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/turnstile")
public class TurnstileController {

    @Autowired
    TurnstileService turnstileService;

    @PostMapping
    public HttpEntity<?> addTurnstile(@RequestBody TurnstileDto turnstileDto){
        Result result =  turnstileService.addTurnstile(turnstileDto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @GetMapping
    public HttpEntity<?> getAllTurnstiles(){
        return turnstileService.getAllTurnstiles();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTurnstile(@PathVariable UUID id){
        return turnstileService.getTurnstile(id);
    }

    @GetMapping("/byuser/{id}")
    public HttpEntity<?> getAllTurnstileByUserId(@PathVariable UUID id,@RequestParam Timestamp needTime){
        return turnstileService.getTurnstileByUserIdAndTime(id,needTime);
    }

    @PutMapping("/{userId}")
    public HttpEntity<?> updateTurnstile(@PathVariable UUID userId, @RequestBody TurnstileDto turnstileDto) {
        Result result = turnstileService.updateTurnstile(userId, turnstileDto);
        return ResponseEntity.status(result.isSuccess()?202:409).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTurnstile(@PathVariable UUID id){
        Result result = turnstileService.deleteTurnstile(id);
        return ResponseEntity.status(result.isSuccess()?204:409).body(result);
    }
}
