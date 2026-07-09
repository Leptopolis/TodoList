package com.example.todoList.service;


import com.example.todoList.entity.Todo;
import com.example.todoList.dto.TodoRequestDto;
import com.example.todoList.enums.TodoStatus;

import java.util.List;
//Интерфейс с методами: create, update, delete, getById, getAll, changeStatus

public interface TodoService{

    public Todo create(TodoRequestDto request);
    public Todo create(Todo todo);

    public Todo update(Long id, TodoRequestDto request);

    public void delete(Long id);

    public Todo getById(Long Id);

    public List<Todo> getAll();

    public Todo changeStatus(Long Id, TodoStatus status);

}