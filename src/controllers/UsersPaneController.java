package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.User;
import services.AlertService;
import services.HashService;
import services.UserService;
import utils.ApplicationUtilities;
import validators.EmptyValidator;
import validators.NameValidator;
import validators.PasswordValidator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersPaneController implements Initializable {
    @FXML
    private TextField tfUserName;

    @FXML
    private TextField tfUserLogin;

    @FXML
    private TextField tfUserPassword;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    private boolean editMode = false;
    private User source;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUserLogin.setDisable(true);

        refreshContent();
    }

    public void refreshContent() {
        User activeUser = ApplicationUtilities.getInstance().getActiveUser();

        this.source = activeUser;

        tfUserName.setText(activeUser.getName());
        tfUserLogin.setText(activeUser.getLogin());

        updateFields();
    }

    public void handleEditUser() {
        setEditMode(true);
    }

    public void handleConfirmChanges() {
        List<String> errors = new ArrayList<>();

        validateFields(errors);

        if (!errors.isEmpty()) {
            AlertService.showWarning(ApplicationUtilities.getInstance().formatErrorMessage(errors));
        } else {
            String password = tfUserPassword.getText().trim();

            source.setName(tfUserName.getText());
            source.setLogin(tfUserLogin.getText());
            source.setPassword(password.isEmpty() ? password : HashService.hash(password));

            try {
                UserService.updateUser(source);
            } catch (Exception e) {
                ApplicationUtilities.getInstance().handleException(e);
            }

            ApplicationUtilities.getInstance().setActiveUser(source);

            setEditMode(false);
            refreshContent();
        }
    }

    public void handleCancelChanges() {
        setEditMode(false);
        refreshContent();
    }

    private void validateFields(List<String> errors) {
        String password = tfUserPassword.getText();
        String confirmPassword = tfConfirmPassword.getText();

        if (!NameValidator.validate(tfUserName.getText())) {
            errors.add("É necessário informar um nome completo");
        }

        if (PasswordValidator.validate(password)) {
            errors.add("A senha deve ter mais de 4 caracteres");
        }

        if (!password.equals(confirmPassword)) {
            errors.add("A senha e a confirmação de senha devem ser iguais");
        }
    }

    private void setEditMode(boolean value) {
        this.editMode = value;
        updateFields();
    }

    private void updateFields() {
        tfUserName.setDisable(!editMode);
        tfUserPassword.setDisable(!editMode);
        tfConfirmPassword.setDisable(!editMode);
        editButton.setDisable(editMode);
        submitButton.setDisable(!editMode);
        cancelButton.setDisable(!editMode);
    }
}
