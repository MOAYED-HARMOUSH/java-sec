package Atm.service;

import Atm.model.Keys;
import Atm.model.User;
import Atm.session.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PublicKey;

public class Login {
    public void login(PrintWriter writer, BufferedReader reader) throws Exception {
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
        User isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated.getUserId()!=0) {
            int userId = isAuthenticated.getUserId(); // استرجاع معرف المستخدم
            String sessionId = SessionManager.createSession(userId);

            writer.println("Login successful! Your session ID is: " + sessionId +" userID ="+userId);

             System.out.println("User logged in: " + username);
        } else {
            writer.println("Invalid username or password. Please try again.");
        }
    }
}
