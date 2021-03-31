package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import views.Main;

import java.io.IOException;

public class SignupController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signinButton;

    public SignupController() {

    }

    public void handleSignin() throws IOException {
        Main main = new Main();
        main.changeScene("/views/login.fxml");
    }
}
