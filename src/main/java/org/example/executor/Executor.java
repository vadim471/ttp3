package org.example.executor;

import java.sql.*;

import static org.example.service.DBService.createPostgresqlConnection;

public class Executor {
    private static Connection connection;

    public Executor(Connection connection)
    {
        this.connection = connection;
    }

    private static void checkConnection(){
        if (connection == null){
            connection = createPostgresqlConnection();
        }
    }
    public void execUpdate(String q_update, PrepareHandler preparer)
    {
        try(PreparedStatement preparedStatement = connection.prepareStatement(q_update)){
            preparer.prepare(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public <T> T execQuery(String q_update, PrepareHandler preparer, ResultHandler<T> handler)
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(q_update)) {
            preparer.prepare(preparedStatement);
            ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                return null;
            }
            T value = handler.handle(result);
            return value;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
