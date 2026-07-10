package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    // этот объект имитирует http-запросы к контроллеру
    @Autowired
    private MockMvc mockMvc;

    // вместо реального сервиса используется заглушка (mock)
    // она не ходит в базу, а возвращает заданные данные
    @MockBean
    private UserService userService;

    // преобразует объекты в json и обратно
    @Autowired
    private ObjectMapper objectMapper;

    // тест для создания пользователя
    @SuppressWarnings("null")
    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        // данные, которые отправляются в запросе
        User user = new User();
        user.setFirstName("Анна");
        user.setLastName("Зайцева");
        user.setEmail("anna@example.com");
        user.setPasswordHash("676767");

        // данные, которые должен вернуть сервис (как будто сохранил в бд)
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("Анна");
        savedUser.setLastName("Зайцева");
        savedUser.setEmail("anna@example.com");
        savedUser.setPasswordHash("676767");
        savedUser.setRole("USER");
        savedUser.setCreatedAt(LocalDateTime.now());
        savedUser.setUpdatedAt(LocalDateTime.now());

        // настраивается заглушка: при вызове createuser вернуть saveduser
        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        // выполняется post-запрос и проверяется ответ
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())               // статус 201
                .andExpect(jsonPath("$.id").value(1L))         // id в ответе = 1
                .andExpect(jsonPath("$.firstName").value("Анна"))
                .andExpect(jsonPath("$.email").value("anna@example.com"));
    }

    // тест для получения пользователя по id
    @Test
    void getUserById_shouldReturnUser() throws Exception {
        // данные, которые должен вернуть сервис
        User user = new User();
        user.setId(1L);
        user.setFirstName("Анна");
        user.setLastName("Зайцева");
        user.setEmail("anna@example.com");
        user.setPasswordHash("676767");
        user.setRole("USER");

        // при вызове getuserbyid(1) вернуть user
        when(userService.getUserById(1L)).thenReturn(user);

        // выполняется get-запрос и проверяется ответ
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())                   // статус 200
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Анна"))
                .andExpect(jsonPath("$.email").value("anna@example.com"));
    }
}