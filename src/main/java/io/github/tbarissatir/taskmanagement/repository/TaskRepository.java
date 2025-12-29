package io.github.tbarissatir.taskmanagement.repository;

import io.github.tbarissatir.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
