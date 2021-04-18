package controllers;

import common.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.LoginService;
import services.SceneChangerService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupLink;

    public LoginController() {

    }

    public void handleSignup() {
        SceneChangerService.changeSceneTo("signup.fxml");
    }

    public void handleLogin() {
        try {
            String login = loginField.getText();
            String password = passwordField.getText();

            LoginService.doLogin(new Credentials(login, password));

            SceneChangerService.changeSceneTo("dashboard.fxml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
