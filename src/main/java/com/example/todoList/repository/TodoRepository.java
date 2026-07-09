package com.example.todoList.repository;


import com.example.todoList.entity.Todo;
import com.example.todoList.enums.TodoStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
    Optional<Todo> findByName(String name);

    List<Todo> findByStatus(TodoStatus status);


   /* @Query("SELECT o FROM Todo WHERE o.status = :status AND o.createdAt < : time")
    List<Todo> findByStatusAndCreatedAtBefore(
        @Param("status") TodoStatus status,
        @Param("createdAt") LocalDateTime createddAt
    );

    @Modifying
    @Query("UPDATE Todo o SET o.status = :status WHERE o.id = :todoId")
    int updateStatus(
        @Param("todoId") Long todoId,
        @Param("status") TodoStatus status
    );*/


    
}
