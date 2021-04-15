package utils;

import models.User;

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
}
