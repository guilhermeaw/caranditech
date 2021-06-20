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
import validators.LoginValidator;
import validators.NameValidator;
import validators.PasswordValidator;

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
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!LoginValidator.validate(loginField.getText())) {
            errors.add("É necessário informar um login com pelo menos 4 caracteres");
        }

        if (!NameValidator.validate(nameField.getText())) {
            errors.add("É necessário informar o nome completo");
        }

        if (!PasswordValidator.validate(password)) {
            errors.add("A senha deve ter mais de 4 caracteres");
        }

        if (!password.equals(confirmPassword)) {
            errors.add("A senha e a confirmação de senha devem ser iguais");
        }
    }
}
