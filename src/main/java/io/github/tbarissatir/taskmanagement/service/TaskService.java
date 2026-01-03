package io.github.tbarissatir.taskmanagement.service;

import io.github.tbarissatir.taskmanagement.dto.TaskRequestDto;
import io.github.tbarissatir.taskmanagement.dto.TaskResponseDto;
import io.github.tbarissatir.taskmanagement.model.Task;
import io.github.tbarissatir.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.github.tbarissatir.taskmanagement.exception.TaskNotFoundException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public TaskResponseDto create(TaskRequestDto dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());
        return map(repository.save(task));
    }

    public Page<TaskResponseDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::map);
    }

    private TaskResponseDto map(Task t) {
        return new TaskResponseDto(t.getId(), t.getTitle(), t.getDescription(), t.getStatus());
    }

    public TaskResponseDto update(Long id, TaskRequestDto dto) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());
        return map(repository.save(task));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }

}
