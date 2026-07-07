package com.example.taskservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

// этот класс отправляет http-запросы в сервис пользователей
@Component
public class UserServiceClient {

    // клиент для выполнения http-запросов
    private final RestClient restClient;

    // создаётся клиент с базовым url user service (порт 8081)
    public UserServiceClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8081")
                .build();
    }

    // проверяет, существует ли пользователь с указанным id
    // отправляет get запрос на /users/{id}
    // если ответ 200 - пользователь есть, если 404 или ошибка - нет
    public boolean userExists(Long userId) {
        try {
            restClient.get()
                    .uri("/users/{id}", userId)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}