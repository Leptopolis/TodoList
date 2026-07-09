package com.example.todoList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.example.todoList.repository") 
public class TodoListApplication {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // 1. Запускаем Spring
        springContext = SpringApplication.run(TodoListApplication.class, args);

        // 2. Запускаем JavaFX в отдельном потоке
        Application.launch(JavaFXApp.class, args);
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    public static class JavaFXApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // Получаем Spring-контекст
            ConfigurableApplicationContext ctx = TodoListApplication.getSpringContext();

            // Загружаем FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main.fxml"));
            loader.setControllerFactory(ctx::getBean);

            Parent root = loader.load();
            primaryStage.setTitle("Todo List");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        }

        @Override
        public void stop() {
            // Закрываем Spring при выходе
            ConfigurableApplicationContext ctx = TodoListApplication.getSpringContext();
            if (ctx != null) {
                ctx.close();
            }
        }
    }
}