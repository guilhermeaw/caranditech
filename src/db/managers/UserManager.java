package db.managers;

import db.Database;
import db.transactions.UserManagerTransactions;
import models.User;

import java.util.List;

public class UserManager implements DefaultManager<User> {
    private static UserManager instance;
    private static UserManagerTransactions transactions;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }

        return instance;
    }

    private UserManager() {
        transactions = new UserManagerTransactions();
    }

    @Override
    public void create(User user) {
        Database db = Database.getInstance();

        try {
            transactions.create(user, db);
        } catch (Exception e) {

        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
