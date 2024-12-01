package Atm.dao;

import Atm.model.User;
import Atm.utils.AESEncryptionUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private User user;

    public User getUserByUsernameAndPassword(String username, String password) throws Exception {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                System.out.println("hi");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                byte[] encryptedPasswordFromDB = resultSet.getBytes("encrypted_password");
                byte[] decryptedPassword = AESEncryptionUtil.encrypt(password);
                    System.out.println("encryptedPasswordFromDB"+ Arrays.toString(encryptedPasswordFromDB));
                System.out.println("decryptedPassword"+ Arrays.toString(decryptedPassword));

                if (Arrays.equals(encryptedPasswordFromDB, decryptedPassword)) {
                    int userId = resultSet.getInt("user_id");
                    byte[] encryptedBalance = resultSet.getBytes("encrypted_balance");
                    return new User(userId, username, encryptedPasswordFromDB, new String(encryptedBalance));
                }
            }
        }
        return null;
    }

    public User getUserByUsernameAndPasswordAndKey(String username, String password,String Key) throws Exception {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("hi");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                byte[] encryptedPasswordFromDB = resultSet.getBytes("encrypted_password");
                byte[] decryptedPassword = AESEncryptionUtil.encryptByExternalKey(password,Key);
                System.out.println("encryptedPasswordFromDB"+ Arrays.toString(encryptedPasswordFromDB));
                System.out.println("decryptedPassword"+ Arrays.toString(decryptedPassword));

                if (Arrays.equals(encryptedPasswordFromDB, decryptedPassword)) {
                    int userId = resultSet.getInt("user_id");
                    byte[] encryptedBalance = resultSet.getBytes("encrypted_balance");
                    return new User(userId, username, encryptedPasswordFromDB, new String(encryptedBalance));
                }
            }
        }
        return null;
    }


    public boolean addUser(String username, String password, String balance) throws Exception {
        String query = "INSERT INTO users (username, encrypted_password, encrypted_balance) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // تشفير البيانات
            byte[] encryptedPassword = AESEncryptionUtil.encrypt(password);
            byte[] encryptedBalance = AESEncryptionUtil.encrypt(balance);

            // إعداد القيم للإدخال
            statement.setString(1, username);
            statement.setBytes(2, encryptedPassword);
            statement.setBytes(3, encryptedBalance);

            // تنفيذ الإدخال
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully: " + username);
                return true;
            } else {
                System.out.println("Failed to add user.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public int getUserIdByUsername(String username) {
//        try {
//            return user.getUserId(username);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 0;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
