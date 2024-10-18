package com.example.asoktmg123;

import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.*;

public class HelloController {
    private Connection connection;
    private TableView<Student> tableView;
    private TextField nameField, ageField;

    public HelloController(TableView<Student> tableView, TextField nameField, TextField ageField) {
        this.tableView = tableView;
        this.nameField = nameField;
        this.ageField = ageField;
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", ""); // Update password if needed
            System.out.println("Database connected!");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            connection = null; // Ensure connection is null if it fails
        }
    }

    public void loadStudentData() {
        try {
            String query = "SELECT * FROM students"; // Ensure your table is named 'students'
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ObservableList<Student> studentList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                studentList.add(new Student(id, name, age));
            }

            tableView.setItems(studentList);
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public void addStudent() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String query = "INSERT INTO students (name, age) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
            loadStudentData(); // Refresh data
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public void updateStudent(Student student) {
        if (student == null) {
            System.out.println("No student selected to update.");
            return;
        }

        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String query = "UPDATE students SET name = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, student.getId());
            preparedStatement.executeUpdate();
            loadStudentData(); // Refresh data
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public void deleteStudent(Student student) {
        if (student == null) {
            System.out.println("No student selected to delete.");
            return;
        }

        try {
            String query = "DELETE FROM students WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.executeUpdate();
            loadStudentData(); // Refresh data
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}
