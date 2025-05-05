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
    
        String role = "";
        String query = "";
        if (email.toLowerCase().contains("@reviewer.com")) {
            role = "reviewers";
            query = "SELECT * FROM reviewers WHERE email = ? AND password = ?";
        } else if (email.toLowerCase().contains("@approver.com")) {
            role = "approvers";
            query = "SELECT * FROM approvers WHERE email = ? AND password = ?";
        } else {
            role = "immigrant";
            query = "SELECT * FROM immigrant WHERE email = ? AND password = ?";
        }
    
        try (var conn = DB_Connection.getConnection();
             var stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, email);
            stmt.setString(2, password);
    
            var rs = stmt.executeQuery();
            if (rs.next()) {
                // Login successful
                switch (role) {
                    case "reviewers" -> new ReviewUI().start(stage);
                    case "approvers" -> new ApprovalUI().start(stage);
                    default -> {
                        // Create and store the logged-in Immigrant
                        Immigrant immigrant = new Immigrant(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("birthdate"),
                            rs.getString("address"),
                            rs.getString("email")
                        );
                        immigrant.setUserID(rs.getInt("userID"));
                        Session.setCurrentImmigrant(immigrant);
        
                        new DashboardUI().start(stage);
                    }
                }
            } else {
                showAlert("Login Failed", "Invalid email or password.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Something went wrong while connecting to the database.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
