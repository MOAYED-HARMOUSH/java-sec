import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        // URL الخاص بالاتصال بـ SQL Server
        String url = "jdbc:sqlserver://localhost:3000;databaseName=atm_simulation;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

        // استعلام SQL لاستخراج البيانات
        String query = "SELECT TOP (1000) [user_id], [username], [encrypted_password], [encrypted_balance] " +
                "FROM [atm_simulation].[dbo].[users]";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // عرض النتائج
            System.out.println("user_id\tusername\t\tencrypted_password\tencrypted_balance");

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String encryptedPassword = resultSet.getString("encrypted_password");
                String encryptedBalance = resultSet.getString("encrypted_balance");

                // طباعة كل سجل
                System.out.println(userId + "\t" + username + "\t" + encryptedPassword + "\t" + encryptedBalance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}