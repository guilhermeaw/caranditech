package utils;

import javafx.scene.control.Alert;
import models.User;

import java.util.List;

public class ApplicationUtilities {
    private static ApplicationUtilities instance;

    public static ApplicationUtilities getInstance() {
        if (instance == null) {
            instance = new ApplicationUtilities();
        }

        return instance;
    }

    private User activeUser;

    private ApplicationUtilities() {}

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public String formatErrorMessage(List<String> errors) {
        String errorMessage = "";

        for (String error : errors) {
            errorMessage += "\n" + error;
        }

        return errorMessage;
    }

    public void handleException(Exception e) {
        String errorMessage = e.getMessage() + "\n\n" + e.getStackTrace().toString();

        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.showAndWait();
    }
}
