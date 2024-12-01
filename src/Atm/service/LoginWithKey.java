package Atm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginWithKey {
    public void LoginWithKey(PrintWriter writer, BufferedReader reader) throws IOException {
        String username, password,Key;

        writer.println("Enter your username: ");
        username = reader.readLine();
        if (username == null || username.isEmpty()) {
            writer.println("Invalid username. Operation aborted.");
            return;
        }

        writer.println("Enter your password: ");
        password = reader.readLine();
        if (password == null || password.isEmpty()) {
            writer.println("Invalid password. Operation aborted.");
            return;
        }
        writer.println("Enter your Key: ");
        Key = reader.readLine();
        if (Key == null || Key.isEmpty()) {
            writer.println("Invalid Key. Operation aborted.");
            return;
        }

        UserService userService = new UserService();
        boolean isAuthenticated = userService.authenticateWithKey(username, password,Key);

        if (isAuthenticated) {
            writer.println("Login successful! Welcome, " + username);
            System.out.println("User logged in: " + username);
        } else {
            writer.println("Invalid username or password. Please try again.");
        }
    }
}
