package com.example.taskservice.controller;

import com.example.taskservice.client.UserServiceClient;
import com.example.taskservice.entity.Task;
import com.example.taskservice.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// этот класс обрабатывает http-запросы по адресу /tasks
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserServiceClient userServiceClient;

    // спринг внедрит оба компонента через конструктор
    public TaskController(TaskService taskService, UserServiceClient userServiceClient) {
        this.taskService = taskService;
        this.userServiceClient = userServiceClient;
    }

    // создание новой задачи
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // получение списка задач пользователя по его id
    @GetMapping
    public List<Task> getTasksByUser(@RequestParam Long userId) {
        return taskService.getTasksByUser(userId);
    }

    // делегирование задачи: назначение исполнителя
    @PostMapping("/{taskId}/delegate")
    public Task delegateTask(@PathVariable Long taskId, @RequestParam Long assigneeId) {
        return taskService.delegateTask(taskId, assigneeId, userServiceClient);
    }
}