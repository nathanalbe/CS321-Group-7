package edu.gmu.cs321;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavigationUI extends Application{
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome");

        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign Up");

        loginButton.setOnAction(e -> {
            LoginUI loginScreen = new LoginUI();
            try {
                loginScreen.createLoginScene(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        signupButton.setOnAction(e -> {
            SignupUI signupScreen = new SignupUI();
            try {
                signupScreen.createSignupScene(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(20, loginButton, signupButton);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
