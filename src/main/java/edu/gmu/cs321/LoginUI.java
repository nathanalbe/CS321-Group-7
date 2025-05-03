package edu.gmu.cs321;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI {
    public void createLoginScene(Stage stage) {
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> handleLogin(emailField.getText(), passwordField.getText(), stage));
        backButton.setOnAction(e -> {
            try {
                new NavigationUI().start(stage);  // Go back to nav screen
            } catch (Exception ex) {
                ex.printStackTrace();
        }});

        VBox layout = new VBox(10, emailLabel, emailField, passwordLabel, passwordField, loginButton, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
    }

    private void handleLogin(String email, String password, Stage stage) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            showAlert("Login Failed", "Please enter email and password.");
            return;
        }

        if (email.toLowerCase().contains("@reviewer.com")) {
            new ReviewUI().start(stage);
        } else {
            new PetitionUI().start(stage);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
