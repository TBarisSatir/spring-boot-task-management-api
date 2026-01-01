package io.github.tbarissatir.taskmanagement.model;

import io.github.tbarissatir.taskmanagement.dto.TaskResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        TODO,
        IN_PROGRESS,
        DONE
    }
}