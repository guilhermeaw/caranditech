package services;

import common.Credentials;
import db.managers.UserManager;
import models.User;

public class UserService {
    public static void createUser(Credentials credentials, String passwordConfirmation, String name) throws Exception {
        User user = new User();

        String login = credentials.getLogin();
        String password = credentials.getPassword();

        if (!password.equals(passwordConfirmation)) {
            AlertService.showWarning("A confirmação de senha deve ser igual a senha informada");
        }

        user.setName(name);
        user.setLogin(login);
        user.setPassword(HashService.hash(password));

        UserManager.getInstance().create(user);
    }

    public static void updateUser(User user) throws Exception {
        UserManager.getInstance().update(user);
    }
}
