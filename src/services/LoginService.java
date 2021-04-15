package services;

import common.Credentials;
import db.managers.UserManager;
import models.User;
import utils.ApplicationUtilities;

public class LoginService {
    public static void doLogin(Credentials credentials) throws Exception {
        String login = credentials.getLogin();
        String password = credentials.getPassword();

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
