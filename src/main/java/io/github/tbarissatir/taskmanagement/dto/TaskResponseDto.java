package io.github.tbarissatir.taskmanagement.dto;

import io.github.tbarissatir.taskmanagement.model.Task;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        Task.Status status
) {}
