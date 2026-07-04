package com.example.taskservice.repository;

import com.example.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// репозиторий для работы с задачами в базе данных
// спринг дата джпа автоматически создаст реализацию этого интерфейса
public interface TaskRepository extends JpaRepository<Task, Long> {

    // найти все задачи, созданные пользователем с переданным id
    // спринг сам сгенерирует запрос по имени метода
    List<Task> findByCreatorId(Long creatorId);

    // найти все задачи, назначенные на пользователя с переданным id
    List<Task> findByAssigneeId(Long assigneeId);
}