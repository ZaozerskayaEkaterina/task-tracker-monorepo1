package com.example.taskservice.controller;

import com.example.taskservice.client.UserServiceClient;
import com.example.taskservice.entity.Task;
import com.example.taskservice.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("null")
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    // имитирует http-запросы
    @Autowired
    private MockMvc mockMvc;

    // заглушка для сервиса
    @MockBean
    private TaskService taskService;

    // заглушка для клиента
    @MockBean
    private UserServiceClient userServiceClient; 

    // для работы с json
    @Autowired
    private ObjectMapper objectMapper;

    // тест для создания задачи
    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        // входные данные
        Task task = new Task();
        task.setTitle("Сделать домашку");
        task.setStatus("TODO");
        task.setCreatorId(1L);
        task.setAssigneeId(1L);

        // то, что вернёт сервис (как будто сохранил)
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Сделать домашку");
        savedTask.setStatus("TODO");
        savedTask.setCreatorId(1L);
        savedTask.setAssigneeId(1L);
        savedTask.setCreatedAt(LocalDateTime.now());
        savedTask.setUpdatedAt(LocalDateTime.now());

        // настройка заглушки
        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        // отправка post-запроса
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Сделать домашку"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    // тест для получения списка задач пользователя
    @Test
    void getTasksByUser_shouldReturnListOfTasks() throws Exception {
        // создаются две тестовые задачи
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Сделать домашку");
        task1.setStatus("TODO");
        task1.setCreatorId(1L);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Сварить кашку");
        task2.setStatus("TODO");
        task2.setCreatorId(1L);

        List<Task> tasks = List.of(task1, task2);

        // при вызове gettasksbyuser(1) вернуть список из двух задач
        when(taskService.getTasksByUser(1L)).thenReturn(tasks);

        // отправка get-запроса с параметром userid
        mockMvc.perform(get("/tasks?userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))          // в ответе 2 элемента
                .andExpect(jsonPath("$[0].title").value("Сделать домашку"))
                .andExpect(jsonPath("$[1].title").value("Сварить кашку"));
    }
}