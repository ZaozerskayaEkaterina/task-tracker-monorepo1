package com.example.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// эта аннотация говорит спрингу, что это главный класс приложения
// она включает автоматическую конфигурацию и сканирование компонентов
@SpringBootApplication
public class TaskServiceApplication {

    // точка входа - запускает приложение
    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }
}