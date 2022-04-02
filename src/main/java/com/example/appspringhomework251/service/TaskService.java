package com.example.appspringhomework251.service;

import com.example.appspringhomework251.config.SecurityConfiguration;
import com.example.appspringhomework251.entity.Task;
import com.example.appspringhomework251.entity.User;
import com.example.appspringhomework251.entity.enums.StatusName;
import com.example.appspringhomework251.payload.Result;
import com.example.appspringhomework251.payload.TaskDto;
import com.example.appspringhomework251.repository.TaskRepository;
import com.example.appspringhomework251.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SecurityConfiguration securityConfiguration;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    public Result addTask(TaskDto taskDto) {
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(taskDto.getDeadline());
        task.setStatusName(StatusName.NEW);
        taskRepository.save(task);
        return new Result("Task saved",true);
    }

    public HttpEntity<?> getAllTasks(){
        return ResponseEntity.ok(taskRepository.findAllByActive(true));
    }

    public HttpEntity<?> getTask(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return ResponseEntity.status(optionalTask.isPresent()?200:409).body(optionalTask.orElse(null));
    }

    public Result updateTask(TaskDto taskDto,UUID id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new Result("Task not found",false);
        Task task = optionalTask.get();
        if (taskDto.getFromUserId()!=null&&taskDto.getToUserId()!=null){
            Optional<User> optionalFromUser = userRepository.findById(taskDto.getFromUserId());
            if (!optionalFromUser.isPresent())
                return new Result("Error. User not found",false);
            User fromUser = optionalFromUser.get();
            Optional<User> optionalToUser = userRepository.findById(taskDto.getToUserId());
            if (!optionalToUser.isPresent())
                return new Result("Error. User not found",false);
            User toUser = optionalToUser.get();
            task.setFromUser(fromUser);
            task.setToUser(toUser);
            authService.sendMessage(toUser.getEmail(),"You have a new task. Please do so until the deadline");
            task.setStatusName(StatusName.IN_PROGRESS);
            taskRepository.save(task);
            return new Result("Task updated",true);
        }
        if (taskDto.isCompleted()){
            task.setStatusName(StatusName.COMPLETED);
            authService.sendMessage(task.getFromUser().getEmail(),"The task with name "+task.getName()+" is completed.");
            taskRepository.save(task);
            return new Result("Task updated",true);
        }
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(taskDto.getDeadline());
        taskRepository.save(task);
        return new Result("Task updated",true);
    }

    public Result deleteTask(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new Result("Task not found",false);
        Task task = optionalTask.get();
        task.setActive(false);
        return null;
    }
}
