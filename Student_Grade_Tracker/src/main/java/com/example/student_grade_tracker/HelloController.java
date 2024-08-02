package com.example.student_grade_tracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private TextField gradeInput;
    @FXML
    private Label averageLabel;
    @FXML
    private Label highestLabel;
    @FXML
    private Label lowestLabel;
    @FXML
    private Label gradeLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button resetButton;

    private List<Double> grades = new ArrayList<>();

    @FXML
    public void addGrade() {
        try {
            double grade = Double.parseDouble(gradeInput.getText());
            grades.add(grade);
            updateStatistics();
            gradeInput.clear();
        } catch (NumberFormatException e) {
            gradeInput.setText("Invalid input");
        }
    }

    @FXML
    public void reset() {
        grades.clear();
        averageLabel.setText("Average: ");
        highestLabel.setText("Highest: ");
        lowestLabel.setText("Lowest: ");
        gradeLabel.setText("Grade: ");
    }

    private void updateStatistics() {
        if (grades.isEmpty()) return;

        double sum = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        for (double grade : grades) {
            sum += grade;
            if (grade > highest) highest = grade;
            if (grade < lowest) lowest = grade;
        }

        double average = sum / grades.size();
        averageLabel.setText(String.format("Average: %.2f", average));
        highestLabel.setText(String.format("Highest: %.2f", highest));
        lowestLabel.setText(String.format("Lowest: %.2f", lowest));
        gradeLabel.setText("Grade: " + getGrade(average));
    }

    private String getGrade(double average) {
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        if(average>=40) return "E";
        return "F";
    }
}
