package db;

import db.fetchers.Fetcher;

import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.util.*;

public class Database {
    private static Database instance = null;
    private static Statement statement;
    private Connection connection = null;

    public Database() {
        try {
            // Load db.properties file config
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

    public void executeCommand( String sql ) throws Exception
    {
        if ( connection != null )
        {
            statement = connection.createStatement();

            statement.execute( sql );

            statement.close();
        }
    }

    public <T> T fetchOne( String sql, Fetcher<T> fetcher ) throws Exception
    {
        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery( sql );

        T t = null;

        if ( resultSet.next() )
        {
            t = fetcher.fetch( resultSet );
        }

        resultSet.close();

        statement.close();

        return t;
    }

    public <T>  List<T> fetchMany( String sql, Fetcher<T> fetcher ) throws Exception
    {
        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery( sql );

        List<T> list = new ArrayList();

        while ( resultSet.next() )
        {
            list.add( fetcher.fetch( resultSet ) );
        }

        resultSet.close();

        statement.close();

        return list;
    }

    public int queryInteger( String sql ) throws Exception
    {
        int result = 0;
        int i = 1;

        statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery( sql );

        while ( resultSet.next() )
        {
            result = resultSet.getInt( i );
        }

        resultSet.close();

        statement.close();

        return result;
    }

    public String quote( Date date )
    {
        if( date == null )
        {
            return "null";
        }

        return "\'" + date.toString()  + "\'";
    }

    public String quote( String sql )
    {
        if( sql == null )
        {
            return "null";
        }

        if( sql.contains( "\'" ) )
        {
            sql = sql.replace( "\'", "\''" );
        }

        return "\'" + sql + "\'";
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
