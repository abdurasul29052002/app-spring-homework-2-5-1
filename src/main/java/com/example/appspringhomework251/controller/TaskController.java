package com.example.appspringhomework251.controller;

import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.TaskDto;
import com.example.appspringhomework251.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public HttpEntity<?> addTask(@Valid @RequestBody TaskDto taskDto){
        Result result = taskService.addTask(taskDto);
        return ResponseEntity.status(result.isSuccess()?201:409).body(result);
    }

    @GetMapping
    public HttpEntity<?> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTask(@PathVariable UUID id){
        return taskService.getTask(id);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateTask(@PathVariable UUID id, @RequestBody TaskDto taskDto) {
        Result result = taskService.updateTask(taskDto, id);
        return ResponseEntity.status(result.isSuccess()? 202:409).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTask(@PathVariable UUID id){
        Result result = taskService.deleteTask(id);
        return ResponseEntity.status(result.isSuccess()?204:409).body(result);
    }
}
