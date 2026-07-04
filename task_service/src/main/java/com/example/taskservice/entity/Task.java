package com.example.taskservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// эта сущность отображается на таблицу tasks в базе данных
@Entity
@Table(name = "tasks")
public class Task {

    // первичный ключ, генерируется базой автоматически
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // название задачи, не может быть пустым
    @Column(nullable = false)
    private String title;

    // статус задачи, по умолчанию 'todo'
    @Column(nullable = false)
    private String status = "TODO";

    // id пользователя, который создал задачу
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    // id исполнителя, может быть null (если ещё не назначен)
    @Column(name = "assignee_id")
    private Long assigneeId;

    // дата создания, автоматически проставляется и не обновляется
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // дата последнего обновления
    private LocalDateTime updatedAt = LocalDateTime.now();

    // геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}