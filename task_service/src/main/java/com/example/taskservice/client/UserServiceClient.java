package com.example.taskservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(
            RestClient.Builder restClientBuilder,
            @Value("${user-service.url}") String userServiceUrl) {
        this.restClient = restClientBuilder
                .baseUrl(userServiceUrl)
                .build();
    }

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