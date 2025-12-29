package io.github.tbarissatir.taskmanagement.controller;

import io.github.tbarissatir.taskmanagement.dto.TaskRequestDto;
import io.github.tbarissatir.taskmanagement.dto.TaskResponseDto;
import io.github.tbarissatir.taskmanagement.service.TaskService;
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
    public ResponseEntity<TaskResponseDto> create(@RequestBody TaskRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }
}
