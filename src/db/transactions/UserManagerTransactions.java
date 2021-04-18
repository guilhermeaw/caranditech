package db.transactions;

import db.Database;
import db.Schema;
import models.User;

import java.util.List;

public class UserManagerTransactions {
    public void create(User user, Database db) throws Exception {
        Schema.Users U = Schema.Users.table;

        String sql = "insert into " + U.name +
                    "(" +
                        U.columns.NAME     + ", " +
                        U.columns.LOGIN    + ", " +
                        U.columns.PASSWORD + ", " +
                        U.columns.STATE    +
                    ")"+
                    " values" +
                    "( " +
                        db.quote(user.getName())     + ", " +
                        db.quote(user.getLogin())    + ", " +
                        db.quote(user.getPassword()) + ", " +
                        user.getState()              +
                    " )";

        db.executeCommand(sql);
    }

    public void update(User user, Database db) throws Exception {
        Schema.Users U = Schema.Users.table;

        String sql = "";

        db.executeCommand(sql);
    }

    public void delete(User user, Database db) throws Exception {

    }

    public User getById(int id, Database db) throws Exception {
        return null;
    }

    public List<User> getAll(Database db) throws Exception {
        return null;
    }

    public User getByLoginAndPassword(String login, String password, Database db) throws Exception {
        Schema.Users U = Schema.Users.table;

        String sql = U.select +
                    " where " + U.columns.LOGIN + " = " + db.quote(login) +
                    " and " + U.columns.PASSWORD + " = " + db.quote(password) +
                    " and " + U.columns.STATE + " != " + User.STATE_DELETED;

        return db.fetchOne(sql, U.fetcher);
    }
}
