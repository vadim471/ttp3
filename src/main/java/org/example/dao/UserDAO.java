package org.example.dao;

import org.example.executor.Executor;
import org.example.executor.ResultHandler;
import org.example.model.UserProfile;

import java.sql.*;

public class UserDAO {
    private final Executor executor;

    public UserDAO (Connection connection){
        this.executor = new Executor(connection);
    }

    
    public UserProfile getUserByLogin(String login){
        return executor.execQuery("select * from users where users.login = ?",
                preparedStatement -> preparedStatement.setString(1, login),
                /*
                new ResultHandler<UserProfile>() {
                    @Override
                    public UserProfile handle(ResultSet resultSet) throws SQLException {
                        UserProfile user = new UserProfile(resultSet.getString("login"),
                                                            resultSet.getString("password"),
                                                            resultSet.getString("email"));
                    }
                 */
                resultSet -> new UserProfile(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"))
        );
    }
    public void addUser(UserProfile user){
        executor.execUpdate("insert into users(login, password, email) values (?, ?, ?)",
                preparedStatement -> {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
        }
        );
    }
}
