package com.example.taskservice.controller;

import com.example.taskservice.entity.Task;
import com.example.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// этот класс обрабатывает http запросы по адресу /tasks
@RestController
@RequestMapping("/tasks")
public class TaskController {

    // внедряем сервис с бизнес-логикой
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // создать новую задачу
    // post запрос на /tasks, данные в теле запроса
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // возвращаем статус 201
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // получить все задачи пользователя
    // get запрос на /tasks?userId=123
    @GetMapping
    public List<Task> getTasksByUser(@RequestParam Long userId) {
        return taskService.getTasksByUser(userId);
    }
}