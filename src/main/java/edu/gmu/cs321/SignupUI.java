package edu.gmu.cs321;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SignupUI {
    private VBox layout;
    private TextField emailField;
    private PasswordField passwordField;
    private TextField firstNameField;
    private TextField lastNameField;
    private DatePicker birthdatePicker;
    private TextField addressField;

    public void createSignupScene(Stage stage) {
        layout = new VBox(12);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-alignment: center;");

        Label titleLabel = new Label("Create an Account");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        emailField = new TextField();
        emailField.setPromptText("Enter your email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Create a password");

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> handleRoleForm(stage));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            try {
                new NavigationUI().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(titleLabel, emailField, passwordField, continueButton, backButton);
        stage.setScene(new Scene(layout, 400, 300));
    }

    private void handleRoleForm(Stage stage) {
        // Email validation
        String email_check = emailField.getText().trim();

        if (!email_check.matches("^[\\w.-]+@[\\w.-]+\\.(com|edu|org)$")) {
            showAlert("Invalid Email", "Please enter a valid email (e.g. name@example.com, name@school.edu, name@nonprofit.org).");
            return;
        }

        // Password length validation
        if (passwordField.getText().length() < 8) {
            showAlert("Weak Password", "Password must be at least 8 characters long.");
            return;
        }

        String email = emailField.getText().toLowerCase();

        layout.getChildren().clear();

        Label roleTitle = new Label("User Information");
        roleTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        layout.getChildren().addAll(roleTitle, firstNameField, lastNameField);

        if (!email.contains("reviewer") && !email.contains("approver")) {
            Label immigrantSection = new Label("Immigrant Details");
            immigrantSection.setStyle("-fx-font-weight: bold; -fx-padding: 10 0 0 0;");

            birthdatePicker = new DatePicker();
            birthdatePicker.setPromptText("Date of Birth");

            addressField = new TextField();
            addressField.setPromptText("Home Address");

            layout.getChildren().addAll(immigrantSection, birthdatePicker, addressField);
        }

        Button submitBtn = new Button("Create Account");
        submitBtn.setOnAction(e -> {
            // Check for empty required fields
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
                showAlert("Missing Information", "First and Last Name are required.");
                return;
            }

            if (!email.contains("reviewer") && !email.contains("approver")) {
                if (birthdatePicker.getValue() == null || addressField.getText().isEmpty()) {
                    showAlert("Missing Information", "Please enter your birthdate and address.");
                    return;
                }

                String address = addressField.getText().trim();

                // Pattern: "123 Main St, Fairfax, VA"
                if (!address.matches("^\\d+\\s+([a-zA-Z]+\\s?)+,\\s*([a-zA-Z]+\\s?)+,\\s*[A-Z]{2}$")) {
                    showAlert("Validation Error", "Please enter a valid address in the format: '123 Main St, Fairfax, VA'");
                    return;
                }

                // Creating new Immigrant and adding to database
                Immigrant testUser = new Immigrant(firstNameField.getText(), lastNameField.getText(), birthdatePicker.getValue().toString(), addressField.getText(), emailField.getText(), passwordField.getText());
                int userID = testUser.createImmigrant();
                if (userID == 0) {
                    showAlert("Database Error","Error failed to add to database and create immigrant");
                }
            }

            String userType = (!email.contains("reviewer") && !email.contains("approver")) ? "Immigrant" : (email.contains("reviewer") ? "Reviewer" : "Approver");

            if (userType.equals("Reviewer")) {
                Reviewer reviewer = new Reviewer(firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText());
                reviewer.createReviewer();
            } else if (userType.equals("Approver")) {
                Approver approver = new Approver(firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText());
                approver.createApprover();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Created");
            alert.setHeaderText(null);
            alert.setContentText(userType + " account created successfully!");
            alert.showAndWait();

            try {
                if (email.toLowerCase().contains("@reviewer.com")) {
                    new ReviewUI().start(stage);
                } else if (email.toLowerCase().contains("@approver.com")) {
                    new ApprovalUI().start(stage);
                } else {
                    new PetitionUI().start(stage);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().add(submitBtn);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
