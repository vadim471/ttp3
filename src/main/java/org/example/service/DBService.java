package org.example.service;

import org.example.dao.UserDAO;
import org.example.executor.Executor;
import org.example.executor.PrepareHandler;
import org.example.executor.ResultHandler;
import org.example.model.UserProfile;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import java.sql.Connection;

public class DBService {
    private final Connection connection;

    public DBService(){
        connection = createPostgresqlConnection();
    }

    public static Connection createPostgresqlConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            Properties dbProperties = new Properties();

            dbProperties.setProperty("user", "postgres");
            dbProperties.setProperty("password", "");
            String url = "jdbc:postgresql://localhost:5432/FileManager";

            Connection connection = DriverManager.getConnection(url, dbProperties);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
    public UserProfile getUserByLogin(String login){
        return (new UserDAO(connection).getUserByLogin(login));
    }

    public void addUser(UserProfile user) {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = new UserDAO(connection);
            dao.addUser(user);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(); //undoes changes
            } catch (SQLException ignore) {
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }
}

