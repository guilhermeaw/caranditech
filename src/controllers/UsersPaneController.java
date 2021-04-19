package controllers;

import db.managers.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.User;
import services.HashService;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();
    }

    public void refreshContent() {
        User activeUser = ApplicationUtilities.getInstance().getActiveUser();

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
            Alert alert = new Alert(Alert.AlertType.ERROR, ApplicationUtilities.getInstance().formatErrorMessage(errors));
            alert.showAndWait();
        } else {
            User activeUser = ApplicationUtilities.getInstance().getActiveUser();

            activeUser.setName(tfUserName.getText());
            activeUser.setLogin(tfUserLogin.getText());
            activeUser.setPassword(HashService.hash(tfUserPassword.getText()));

            try {
                UserManager.getInstance().update(activeUser);
            } catch (Exception e) {
                ApplicationUtilities.getInstance().handleException(e);
            }

            ApplicationUtilities.getInstance().setActiveUser(activeUser);

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
