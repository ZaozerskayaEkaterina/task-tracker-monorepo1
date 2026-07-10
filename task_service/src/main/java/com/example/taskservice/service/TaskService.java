package com.example.taskservice.service;

import com.example.taskservice.client.UserServiceClient;
import com.example.taskservice.entity.Task;
import com.example.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

// этот класс содержит бизнес-логику для работы с задачами
@SuppressWarnings("null")
@Service
public class TaskService {

    // репозиторий для работы с базой данных
    private final TaskRepository taskRepository;

    // спринг внедрит репозиторий через конструктор
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

    // назначает исполнителя на задачу
    // проверяет, что задача существует и пользователь с assigneeId есть в user service
    public Task delegateTask(Long taskId, Long assigneeId, UserServiceClient userServiceClient) {
        // поиск задачи по id, если не найдена - исключение
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + taskId));

        // проверка существования пользователя через http-запрос
        if (!userServiceClient.userExists(assigneeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + assigneeId);
        }

        // назначение исполнителя и сохранение
        task.setAssigneeId(assigneeId);
        return taskRepository.save(task);
    }
}