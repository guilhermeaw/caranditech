package utils;

import models.User;
import services.AlertService;

import java.io.PrintWriter;
import java.io.StringWriter;
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
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        String errorMessage = e.getMessage() + "\n\n" + exceptionAsString;

        AlertService.showError(errorMessage);
    }
}
