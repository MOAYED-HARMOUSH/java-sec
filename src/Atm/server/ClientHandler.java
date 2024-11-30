package Atm.server;

import Atm.service.UserService;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

             String username = null, password = null;

            writer.println("Welcome to the ATM Simulation!");

            writer.println("Enter your username: ");
            username = reader.readLine();
            System.out.println("Received from client: " + username);

            writer.println("Enter your password: ");
            password = reader.readLine();
            System.out.println("Received from client: " + password);

            UserService userService = new UserService();
            boolean isAuthenticated = userService.authenticate(username, password);

            if (isAuthenticated) {
                System.out.println("Login successful! Welcome, " + username);
                writer.println("Login successful! Welcome, " + username);
            } else {
                System.out.println("Invalid username or password. Please try again.");
                writer.println("Invalid username or password. Please try again.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();  // إغلاق الاتصال بعد الانتهاء
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
