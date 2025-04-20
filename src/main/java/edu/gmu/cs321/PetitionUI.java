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

        // === Fiancé(e) Section ===
        Label titleLabel = new Label("Fiancé(e) Information");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField fianceFirstNameField = new TextField();
        fianceFirstNameField.setPromptText("First Name");

        TextField fianceLastNameField = new TextField();
        fianceLastNameField.setPromptText("Last Name");

        DatePicker fianceDOBPicker = new DatePicker();
        fianceDOBPicker.setPromptText("Date of Birth");

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

            ComboBox<String> relationshipBox = new ComboBox<>();
            relationshipBox.setPromptText("Relationship");
            relationshipBox.getItems().add("Child");

            VBox childBox = new VBox(5, childName, childDOB, relationshipBox);
            childBox.setPadding(new Insets(5));
            childBox.setStyle("-fx-border-color: #ccc; -fx-padding: 5;");
            childrenList.getChildren().add(childBox);
        });

        // === Action Buttons ===
        Button saveButton = new Button("Save Draft");
        Button nextButton = new Button("Submit Petition");
        Button cancelButton = new Button("Cancel");

        nextButton.setOnAction(e -> {
            // === Validation for Fiancé(e) ===
            if (fianceFirstNameField.getText().isEmpty() ||
                fianceLastNameField.getText().isEmpty() ||
                fianceDOBPicker.getValue() == null ||
                nationalityBox.getValue() == null) {
                showAlert("Validation Error", "Please complete all required Fiancé(e) fields.");
                return;
            }

            // === Validation for each child block (if any) ===
            for (var childNode : childrenList.getChildren()) {
                if (childNode instanceof VBox childBox) {
                    TextField nameField = (TextField) childBox.getChildren().get(0);
                    DatePicker dobPicker = (DatePicker) childBox.getChildren().get(1);
                    ComboBox<String> relationshipBox = (ComboBox<String>) childBox.getChildren().get(2);

                    if (nameField.getText().isEmpty() ||
                        dobPicker.getValue() == null ||
                        relationshipBox.getValue() == null) {
                        showAlert("Validation Error", "Please complete all required fields for each child.");
                        return;
                    }
                }
            }

            // Placeholder for successful submission logic
            System.out.println("Petition submitted successfully.");
            showAlert("Success", "Petition submitted successfully.");
        });

        cancelButton.setOnAction(e -> {
            fianceFirstNameField.clear();
            fianceLastNameField.clear();
            fianceDOBPicker.setValue(null);
            nationalityBox.getSelectionModel().clearSelection();
            childrenList.getChildren().clear();
        });

        HBox buttonRow = new HBox(10, saveButton, nextButton, cancelButton);
        buttonRow.setPadding(new Insets(10, 0, 0, 0));

        // === Layout ===
        VBox formLayout = new VBox(10,
                titleLabel,
                fianceFirstNameField,
                fianceLastNameField,
                fianceDOBPicker,
                nationalityBox,
                childrenLabel,
                childrenList,
                addChildButton,
                buttonRow
        );
        formLayout.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(formLayout);

        Scene scene = new Scene(scrollPane, 520, 640);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
