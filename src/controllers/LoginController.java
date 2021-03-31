package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import views.Main;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    public LoginController() {

    }

    public void handleSignup() throws IOException {
        Main main = new Main();
        main.changeScene("signup.fxml");
    }

    public void handleLogin() throws Exception {
        Main main = new Main();
        main.changeScene("dashboard.fxml");
    }
}
