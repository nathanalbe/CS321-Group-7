package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.cs321.Workflow;

public class PetitionUI extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
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

        TextField countryField = new TextField();
        countryField.setPromptText("Enter Country");

        // === Children Section ===
        Label childrenLabel = new Label("Children (Optional):");
        VBox childrenList = new VBox(5);
        Button addChildButton = new Button("Add Child");

        addChildButton.setOnAction(e -> {
            TextField childFirstName = new TextField();
            childFirstName.setPromptText("Child First Name");

            TextField childLastName = new TextField();
            childLastName.setPromptText("Child Last Name");

            DatePicker childDOB = new DatePicker();
            childDOB.setPromptText("Child DOB");

            VBox childBox = new VBox(5, childFirstName, childLastName, childDOB);
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
                fianceDOBPicker.getValue() == null) {
                showAlert("Validation Error", "Please complete all required Fiancé(e) fields.");
                return;
            }

            String country = countryField.getText().trim();
            if (!country.matches("^[a-zA-Z\\s\\-]{2,50}$")) {
                showAlert("Validation Error", "Please enter a valid country name (letters, spaces, and dashes only).");
                return;
            }

            

            // === Validation for each child block (if any) ===
            for (var childNode : childrenList.getChildren()) {
                if (childNode instanceof VBox childBox) {
                    TextField firstNameField = (TextField) childBox.getChildren().get(0);
                    TextField lastNameField = (TextField) childBox.getChildren().get(1);
                    DatePicker dobPicker = (DatePicker) childBox.getChildren().get(2);
            
                    if (firstNameField.getText().isEmpty() ||
                        lastNameField.getText().isEmpty() ||
                        dobPicker.getValue() == null) {
                        showAlert("Validation Error", "Please complete all fields for each child.");
                        return;
                    }
                }
            }
            

            // === Petition submission to database ===
            // database connection and insertion logic would go here

            // Logged in immigrant
            Immigrant immigrant = Session.getCurrentImmigrant();
            int userID = immigrant.getUserID();

            // Create Petition and add to database
            Petition petition = new Petition(userID, null, "Pending");
            int petitionID = petition.createPetition();
            if (petitionID == 0) {
                showAlert("Database Error", "Failed to create petition.");
                return;
            }

            // Create Dependent and add to database
            Dependent dependent = new Dependent(fianceFirstNameField.getText(), fianceLastNameField.getText(), fianceDOBPicker.getValue().toString(), "Fiancé(e)");
            int depID = dependent.createDependent(petitionID);
            if (depID == 0) {
                showAlert("Database Error", "Failed to add dependent to the database.");
            }

            for (var childNode : childrenList.getChildren()) {
                if (childNode instanceof VBox childBox) {
                    TextField firstNameField = (TextField) childBox.getChildren().get(0);
                    TextField lastNameField = (TextField) childBox.getChildren().get(1);
                    DatePicker dobPicker = (DatePicker) childBox.getChildren().get(2);
            
                    if (firstNameField.getText().isEmpty() ||
                        lastNameField.getText().isEmpty() ||
                        dobPicker.getValue() == null) {
                        showAlert("Validation Error", "Please complete all fields for each child.");
                        return;
                    }

                    Dependent child = new Dependent(firstNameField.getText(), lastNameField.getText(), dobPicker.getValue().toString(), "Child");
                    child.createDependent(petitionID);
                }
            }

            // Workflow 
            // === Add to Workflow database ===
            try {
                Workflow workflow = new Workflow();
                int result = workflow.AddWFItem(petition.getPetitionID(), "Review");

                if (result == 0) {
                    showAlert("Success", "Petition submitted and added to workflow for review.");
                    System.out.println("Workflow: Petition " + petition.getPetitionID() + " added to 'Review' queue.");
                } else {
                    showAlert("Workflow Error", "Failed to add to workflow. Code: " + result);
                }

                workflow.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Unexpected error while adding to workflow.");
            }
            // Go to next screen (Review)
            try {
                new ReviewUI().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Navigation Error", "Cannot open review screen.");
            }
        });

        cancelButton.setOnAction(e -> {
            fianceFirstNameField.clear();
            fianceLastNameField.clear();
            fianceDOBPicker.setValue(null);
            countryField.clear();
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
                countryField,
                childrenLabel,
                childrenList,
                addChildButton,
                buttonRow
        );
        formLayout.setPadding(new Insets(20));
        ScrollPane scrollPane = new ScrollPane(formLayout);

        Scene scene = new Scene(scrollPane, 520, 640);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
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
