package Atm.dao;

import Atm.model.User;
import java.sql.*;

public class UserDao {

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND encrypted_password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String encryptedBalance = resultSet.getString("encrypted_balance");
                return new User(userId, username, password.getBytes(), encryptedBalance);
            }
        }
        return null;
    }
}
