package com.example.asoktmg123;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private TableView<Student> tableView;
    private TextField nameField, ageField;
    private HelloController controller;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        tableView = new TableView<>(); // Initialize TableView

        // Define columns
        TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Student, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject());

        tableView.getColumns().addAll(idColumn, nameColumn, ageColumn); // Add columns to TableView

        // Input fields
        nameField = new TextField();
        ageField = new TextField();
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        // Instantiate controller
        controller = new HelloController(tableView, nameField, ageField);

        addButton.setOnAction(e -> controller.addStudent()); // Add action for button
        updateButton.setOnAction(e -> {
            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
            controller.updateStudent(selectedStudent); // Update action
        });
        deleteButton.setOnAction(e -> {
            Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
            controller.deleteStudent(selectedStudent); // Delete action
        });

        // Layout
        VBox vbox = new VBox(tableView, new Label("Name:"), nameField, new Label("Age:"), ageField, addButton, updateButton, deleteButton);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Student Management");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();

        controller.loadStudentData(); // Load data into TableView
    }
}
