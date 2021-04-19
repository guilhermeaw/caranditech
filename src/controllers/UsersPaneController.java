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
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    private boolean editMode = false;
    private User source;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();
    }

    public void refreshContent() {
        User activeUser = ApplicationUtilities.getInstance().getActiveUser();

        this.source = activeUser;

        tfUserName.setText(activeUser.getName());
        tfUserLogin.setText(activeUser.getLogin());
        tfUserPassword.setText(activeUser.getPassword());

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
            String actualPassword = source.getPassword();
            String newPassword = tfUserPassword.getText();

            source.setName(tfUserName.getText());
            source.setLogin(tfUserLogin.getText());
            source.setPassword(actualPassword.equals(newPassword) ? actualPassword : HashService.hash(newPassword));

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
        if (!EmptyValidator.validate(tfUserName.getText())) {
            errors.add("É necessário informar um nome");
        }

        if (!EmptyValidator.validate(tfUserLogin.getText())) {
            errors.add("É necessário informar um login");
        }

        if (!EmptyValidator.validate(tfUserPassword.getText())) {
            errors.add("É necessário informar uma senha");
        }
    }

    private void setEditMode(boolean value) {
        this.editMode = value;

        updateFields();
    }

    private void updateFields() {
        tfUserName.setDisable(!editMode);
        tfUserLogin.setDisable(!editMode);
        tfUserPassword.setDisable(!editMode);
        editButton.setDisable(editMode);
        submitButton.setDisable(!editMode);
        cancelButton.setDisable(!editMode);
    }
}
