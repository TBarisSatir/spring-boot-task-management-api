package io.github.tbarissatir.taskmanagement.dto;
import io.github.tbarissatir.taskmanagement.model.Task;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @NotBlank(message = "title cannot be empty")
        @Size(min = 3, message = "title must be at least 3 characters")
        String title,

        @Size(max = 200, message = "description cannot exceed 200 characters")
        String description,

        Task.Status status
) {}