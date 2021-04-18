package db.fetchers;

import db.Schema;
import models.User;

import java.sql.ResultSet;

public class UserFetcher implements Fetcher<User> {
    @Override
    public User fetch(ResultSet resultSet) throws Exception {
        User user = new User();

        user.setId(resultSet.getInt(Schema.Users.table.columns.ID));
        user.setName(resultSet.getString(Schema.Users.table.columns.NAME));
        user.setLogin(resultSet.getString(Schema.Users.table.columns.LOGIN));
        user.setPassword(resultSet.getString(Schema.Users.table.columns.PASSWORD));
        user.setState(resultSet.getInt(Schema.Users.table.columns.STATE));

        return user;
    }
}
