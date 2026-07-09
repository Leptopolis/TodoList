package com.example.todoList.service;

import com.example.todoList.entity.Todo;
import com.example.todoList.enums.TodoStatus;
import com.example.todoList.repository.TodoRepository;
import com.example.todoList.dto.TodoRequestDto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/*
    public Todo create(TodoRequestDto request);

    public int update(Long id, TodoRequestDto request);

    public int delete(Long id);

    public Todo getById(Long Id);

    public List<Todo> getAll();

    public int changeStatus(Long Id, TodoStatus status);

*/

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService{
    private final TodoRepository repository;

    @Transactional
    @Override
    public Todo create(TodoRequestDto request){
        Todo todo = Todo.builder()
            .name(request.getName())
            .description(request.getDescription())
            .status(TodoStatus.PENDING)
            .deadline(request.getDeadline())
            .build();
        return repository.save(todo);
    }

    @Transactional
    @Override
    public Todo create(Todo todo){
        return repository.save(todo);
    }

    @Transactional
    @Override
    public Todo changeStatus(Long id, TodoStatus status){
        Todo todo = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tusk is not found id: " + id));
        todo.setStatus(status);
        return repository.save(todo);
    }

    @Transactional
    @Override
    public Todo update(Long id, TodoRequestDto request){
        Todo todo = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tusk is not found id: " + id));

        if(request.getName() != null){
            todo.setName(request.getName());
        }
        if(request.getDescription() !=  null){
            todo.setDescription(request.getDescription());
        }
        if(request.getDeadline() !=  null){
            todo.setDeadline(request.getDeadline());
        }
        return repository.save(todo);
    }

    @Transactional
    @Override
    public void delete(Long id){
        Todo todo = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tusk is not found id: " + id));
        repository.delete(todo);
    }

    @Override
    public Todo getById(Long id){
        Todo todo = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tusk is not found id: " + id));
        return todo;
    }

    @Override
    public List<Todo> getAll(){
        return repository.findAll();
    }
}