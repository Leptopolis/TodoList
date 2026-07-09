package com.example.todoList.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

import com.example.todoList.enums.TodoStatus;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponseDto{
    @Positive(message = "Todo ID must be positive")
    private Long id;

    @NotBlank(message = "Toddo must have a title")
    private String name;

    private String description;

    private TodoStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deadline;
}

