package Atm.server;

import Atm.service.Login;
import Atm.service.SignUP;

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

            // إرسال رسالة الاختيار إلى العميل
            writer.println("Welcome to the ATM!");
            writer.println("To login, enter 1.");
            writer.println("To sign up, enter 2.");
            writer.println("Please enter your choice: ");

            // قراءة اختيار المستخدم
            String choiceInput = reader.readLine();
            if (choiceInput == null) {
                writer.println("Invalid input. Connection closing.");
                return;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                writer.println("Invalid choice. Please enter a valid number.");
                return;
            }

            // تنفيذ العملية بناءً على اختيار المستخدم
            switch (choice) {
                case 1:
                    Login userLogin = new Login();
                    userLogin.login(writer, reader);
                    break;

                case 2:
                    SignUP signUp = new SignUP();
                    signUp.signUp(writer, reader);
                    break;

                default:
                    writer.println("Invalid choice. Please restart and try again.");
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
