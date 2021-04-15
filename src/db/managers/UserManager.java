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
    public void create(User user) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.create(user, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void update(User user) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.update(user, db);
        } finally {
            db.release();
        }
    }

    @Override
    public void delete(User user) throws Exception {
        Database db = Database.getInstance();

        try {
            transactions.delete(user, db);
        } finally {
            db.release();
        }
    }

    @Override
    public User getById(int id) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getById(id, db);
        } finally {
            db.release();
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getAll(db);
        } finally {
            db.release();
        }
    }

    public User getByLoginAndPassword(String login, String password) throws Exception {
        Database db = Database.getInstance();

        try {
            return transactions.getByLoginAndPassword(login, password, db);
        } finally {
            db.release();
        }
    }
}
