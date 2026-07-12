package com.example.todoList.ui;

import com.example.todoList.entity.Todo;
import com.example.todoList.enums.TodoStatus;
import com.example.todoList.service.TodoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainController {

    private final TodoService todoService;

    @FXML private TableView<Todo> todoTable;
    @FXML private TableColumn<Todo, Long> colId;
    @FXML private TableColumn<Todo, String> colTitle;
    @FXML private TableColumn<Todo, String> colDescription;
    @FXML private TableColumn<Todo, TodoStatus> colStatus;
    @FXML private TableColumn<Todo, String> colDeadline;

    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<TodoStatus> statusCombo;
    @FXML private DatePicker deadlinePicker;

    @FXML
    private ComboBox<TodoStatus> statusComboBox;

    private ObservableList<Todo> todoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Настройка колонок
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        // Заполняем ComboBox статусами
        statusCombo.setItems(FXCollections.observableArrayList(TodoStatus.values()));
        statusCombo.setValue(TodoStatus.PENDING);

        ObservableList<TodoStatus> statuses = FXCollections.observableArrayList(TodoStatus.values());
        statusComboBox.setItems(statuses);
        statusComboBox.setValue(TodoStatus.PENDING); // значение по умолчанию

        // Загружаем данные
        loadTodos();
    }

    private void loadTodos() {
        todoList.setAll(todoService.getAll());
        todoTable.setItems(todoList);
    }

    @FXML
    public void addTodo() {
        String title = titleField.getText();
        if (title == null || title.isBlank()) {
            showAlert("Ошибка", "Заголовок обязателен");
            return;
        }

        Todo todo = new Todo();
        todo.setName(title);
        todo.setDescription(descriptionArea.getText());
        todo.setStatus(statusCombo.getValue());
        if (deadlinePicker.getValue() != null) {
            todo.setDeadline(deadlinePicker.getValue().atStartOfDay());
        }

        todoService.create(todo);
        clearFields();
        loadTodos();
    }

    @FXML
    public void changeStatus(){
        Todo selected = todoTable.getSelectionModel().getSelectedItem();
        TodoStatus selectedStatus = statusComboBox.getValue();
        if(selectedStatus == TodoStatus.COMPLETED){
            todoService.delete(selected.getId());
            loadTodos();
            return;
        }
        todoService.changeStatus(selected.getId(), selectedStatus);
        loadTodos();
    }

    @FXML
    public void deleteTodo() {
        Todo selected = todoTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите задачу для удаления");
            return;
        }
        todoService.delete(selected.getId());
        loadTodos();
    }

    @FXML
    public void markCompleted() {
        Todo selected = todoTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите задачу");
            return;
        }
        todoService.changeStatus(selected.getId(), TodoStatus.COMPLETED);
        loadTodos();
    }

    private void clearFields() {
        titleField.clear();
        descriptionArea.clear();
        statusCombo.setValue(TodoStatus.PENDING);
        deadlinePicker.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}