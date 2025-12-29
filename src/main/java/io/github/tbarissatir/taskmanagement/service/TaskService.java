package io.github.tbarissatir.taskmanagement.service;

import io.github.tbarissatir.taskmanagement.dto.TaskRequestDto;
import io.github.tbarissatir.taskmanagement.dto.TaskResponseDto;
import io.github.tbarissatir.taskmanagement.model.Task;
import io.github.tbarissatir.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public TaskResponseDto create(TaskRequestDto dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(Task.Status.TODO);
        return map(repository.save(task));
    }

    public Page<TaskResponseDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::map);
    }

    private TaskResponseDto map(Task t) {
        return new TaskResponseDto(t.getId(), t.getTitle(), t.getDescription(), t.getStatus());
    }
}
