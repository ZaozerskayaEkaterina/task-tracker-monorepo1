package com.example.taskservice.service;

import com.example.taskservice.entity.Task;
import com.example.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// аннотация @service указывает спрингу, что этот класс содержит бизнес-логику
@Service
public class TaskService {

    // репозиторий для работы с базой данных
    private final TaskRepository taskRepository;

    // конструктор для внедрения зависимости репозитория
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // сохраняет новую задачу в базу данных
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // возвращает все задачи, созданные пользователем с указанным id
    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByCreatorId(userId);
    }

    // обновляет существующую задачу
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }
}