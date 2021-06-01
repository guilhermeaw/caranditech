package utils;

import javafx.stage.Window;
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
    private Window window;

    private ApplicationUtilities() {}

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public Window getWindow()
    {
        return window;
    }

    public String getCompany()
    {
        return "CarandiTech - Gestão Prisional";
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
        e.printStackTrace();
    }
}
