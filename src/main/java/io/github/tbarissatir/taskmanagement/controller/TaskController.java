package io.github.tbarissatir.taskmanagement.controller;

import io.github.tbarissatir.taskmanagement.dto.TaskRequestDto;
import io.github.tbarissatir.taskmanagement.dto.TaskResponseDto;
import io.github.tbarissatir.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
