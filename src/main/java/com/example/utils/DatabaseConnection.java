package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.config.AppConfig;

public class DatabaseConnection {

    private Connection connection;
    private PreparedStatement queryUsername;
    private PreparedStatement queryPassword;
    private ResultSet resultSet;

    public DatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(AppConfig.DB_CONNECTION_STRING, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD);
        queryUsername = connection.prepareStatement("SELECT * FROM " + AppConfig.DB_TABLE + " WHERE username = ?");
        queryPassword = connection.prepareStatement("SELECT * FROM " + AppConfig.DB_TABLE + " WHERE username = ? AND password = ?");
    }

    public boolean isValidUser(String username) throws SQLException {
        queryUsername.setString(1, username);
        resultSet = queryUsername.executeQuery();
        return resultSet.next();
    }

    public boolean isValidPassword(String username, String password) throws SQLException {
        queryPassword.setString(1, username);
        queryPassword.setString(2, password);
        resultSet = queryPassword.executeQuery();
        return resultSet.next();
    }

    public void close() throws SQLException {
        connection.close();
    }

}
