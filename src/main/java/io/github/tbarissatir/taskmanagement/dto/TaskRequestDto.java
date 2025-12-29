package io.github.tbarissatir.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @NotBlank String title,
        String description
) {}
