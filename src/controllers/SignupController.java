package controllers;

import common.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.SceneChangerService;
import services.UserService;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signinLink;

    public SignupController() {
    }

    public void handleSignup() {
        try {
            String login = loginField.getText();
            String password = passwordField.getText();
            String passwordConfirmation = confirmPasswordField.getText();

            UserService.createUser(new Credentials(login, password), passwordConfirmation, "Guilherme");

            SceneChangerService.changeSceneTo("login.fxml");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleSignin() {
        SceneChangerService.changeSceneTo("login.fxml");
    }
}
