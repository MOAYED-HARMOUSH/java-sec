package Atm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Login {
    public void login(PrintWriter writer, BufferedReader reader) throws IOException {
        String username, password;

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

        UserService userService = new UserService();
        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            writer.println("Login successful! Welcome, " + username);
            System.out.println("User logged in: " + username);
        } else {
            writer.println("Invalid username or password. Please try again.");
        }
    }
}
