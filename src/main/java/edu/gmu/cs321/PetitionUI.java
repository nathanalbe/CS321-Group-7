package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PetitionUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Petition for Fiancé(e) and Children");

        // === Form Fields ===
        Label titleLabel = new Label("Fiancé(e) Information");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField fianceNameField = new TextField();
        fianceNameField.setPromptText("Full Name");

        DatePicker fianceDOBPicker = new DatePicker();
        ComboBox<String> nationalityBox = new ComboBox<>();
        nationalityBox.setPromptText("Select Nationality");
        nationalityBox.getItems().addAll("Ethiopia", "USA", "Canada", "Other");

        // === Children Section ===
        Label childrenLabel = new Label("Children (Optional):");
        VBox childrenList = new VBox(5);
        Button addChildButton = new Button("Add Child");

        addChildButton.setOnAction(e -> {
            TextField childName = new TextField();
            childName.setPromptText("Child Name");

            DatePicker childDOB = new DatePicker();
            childDOB.setPromptText("Child DOB");

            VBox childBox = new VBox(2, childName, childDOB);
            childBox.setStyle("-fx-border-color: #ccc; -fx-padding: 5;");
            childrenList.getChildren().add(childBox);
        });

        // === Action Buttons ===
        Button saveButton = new Button("Save Draft");
        Button nextButton = new Button("Next");
        Button cancelButton = new Button("Cancel");

        nextButton.setOnAction(e -> {
            if (fianceNameField.getText().isEmpty() || fianceDOBPicker.getValue() == null) {
                showAlert("Validation Error", "Please fill out the required fiancé(e) fields.");
                return;
            }
            System.out.println("Form submitted.");
        });

        cancelButton.setOnAction(e -> {
            fianceNameField.clear();
            fianceDOBPicker.setValue(null);
            nationalityBox.getSelectionModel().clearSelection();
            childrenList.getChildren().clear();
        });

        HBox buttonRow = new HBox(10, saveButton, nextButton, cancelButton);
        buttonRow.setPadding(new Insets(10, 0, 0, 0));

        // === Layout ===
        VBox formLayout = new VBox(10,
                titleLabel,
                fianceNameField,
                fianceDOBPicker,
                nationalityBox,
                childrenLabel,
                childrenList,
                addChildButton,
                buttonRow
        );
        formLayout.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(formLayout);

        Scene scene = new Scene(scrollPane, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
