package db;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Database {
    private static Database instance = null;
    private Connection connection = null;

    public Database() {
        try {
            // Load db.porperties file configs
            Properties prop = new Properties();
            prop.load(new FileInputStream("db.properties"));
            String dbDriver = prop.getProperty("db.driver");
            String dbUrl = prop.getProperty("db.url");
            String dbUser = prop.getProperty("db.user");
            String dbPassword = prop.getProperty("db.password");

            // Load database driver
            Class.forName(dbDriver);

            if (dbUser.length() != 0) // Connection with user and password
            {
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            } else {
                connection = DriverManager.getConnection(dbUrl);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Returns the instance
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // returns the connection
    public Connection getConnection() {
        if (connection == null) {
            throw new RuntimeException("connection cannot be null");
        }
        return connection;
    }

    // Closes connection
    public void release() {
        try {
            connection.close();
            instance = null;
            connection = null;
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
