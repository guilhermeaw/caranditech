package controllers;

import common.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AlertService;
import services.SceneChangerService;
import services.UserService;
import utils.ApplicationUtilities;
import validators.EmptyValidator;

import java.util.ArrayList;
import java.util.List;

public class SignupController {
    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    public SignupController() {
    }

    public void handleSignup() {
        try {
            String login = loginField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();
            String passwordConfirmation = confirmPasswordField.getText();

            List<String> errors = new ArrayList<>();

            validateFields(errors);

            if (!errors.isEmpty()) {
                AlertService.showWarning(ApplicationUtilities.getInstance().formatErrorMessage(errors));
            } else {
                UserService.createUser(new Credentials(login, password), passwordConfirmation, name);

                SceneChangerService.changeSceneTo("login.fxml");
            }

        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleSignin() {
        SceneChangerService.changeSceneTo("login.fxml");
    }

    private void validateFields(List<String> errors) {
        if (!EmptyValidator.validate(loginField.getText())) {
            errors.add("É necessário informar um login");
        }

        if (!EmptyValidator.validate(nameField.getText())) {
            errors.add("É necessário informar um nome");
        }

        if (!EmptyValidator.validate(passwordField.getText())) {
            errors.add("É necessário informar uma senha");
        }

        if (!EmptyValidator.validate(confirmPasswordField.getText())) {
            errors.add("É necessário informar a confirmação de senha");
        }
    }
}
