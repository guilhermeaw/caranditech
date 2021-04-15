package services;

import db.managers.UserManager;
import models.User;
import utils.ApplicationUtilities;

public class LoginService {
    public static void doLogin(String login, String password) throws Exception {
        if (login == null || password == null) {
            // TODO
        }

        User user = UserManager.getInstance()
                               .getByLoginAndPassword(login, HashService.hash(password));

        if (user != null) {
            ApplicationUtilities.getInstance().setActiveUser(user);
        } else {
            throw new Exception("Usuário ou senha incorretos");
        }
    }
}
