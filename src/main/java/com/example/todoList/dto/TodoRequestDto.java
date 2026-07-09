package com.example.todoList.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequestDto{

    @NotBlank(message = "Todo must have a name")
    private String name;

    private String description;

    @Future
    private LocalDateTime deadline;

}