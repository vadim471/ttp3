package org.example.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PrepareHandler {
    void prepare (PreparedStatement preparedStatement) throws SQLException;
}
