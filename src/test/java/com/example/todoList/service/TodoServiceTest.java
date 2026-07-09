package com.example.todoList.service;

import com.example.todoList.entity.Todo;
import com.example.todoList.enums.TodoStatus;
import com.example.todoList.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Включаем поддержку Mockito
class TodoServiceTest {

    @Mock
    private TodoRepository repository;  // Заглушка для репозитория

    @InjectMocks
    private TodoServiceImpl todoService;  // Тестируемый сервис

    private Todo testTodo;

    @BeforeEach
    void setUp() {
        testTodo = new Todo();
        testTodo.setId(1L);
        testTodo.setName("Тестовая задача");
        testTodo.setStatus(TodoStatus.PENDING);
    }

    @Test
    @DisplayName("Создание задачи должно вернуть сохранённую задачу")
    void create_ShouldReturnSavedTodo() {
        // 1. Настройка поведения мока
        when(repository.save(any(Todo.class))).thenReturn(testTodo);

        // 2. Вызов метода
        Todo result = todoService.create(testTodo);

        // 3. Проверки
        assertNotNull(result);
        assertEquals("Тестовая задача", result.getName());
        assertEquals(TodoStatus.PENDING, result.getStatus());

        verify(repository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("Поиск по ID должен вернуть задачу, если она существует")
    void getById_WhenExists_ShouldReturnTodo() {
        when(repository.findById(1L)).thenReturn(Optional.of(testTodo));

        Todo result = todoService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Поиск по ID должен выбросить исключение, если задача не найдена")
    void getById_WhenNotExists_ShouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> todoService.getById(99L));
    }

    @Test
    void delete_ShouldCallDeleteById() {
    // Настройка мока: findById() должен вернуть задачу
    Todo todo = new Todo();
    todo.setId(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(todo));
    
    // Настройка мока: delete() ничего не делает
    doNothing().when(repository).delete(any(Todo.class));

    todoService.delete(1L);

    verify(repository, times(1)).delete(any(Todo.class));
}
}