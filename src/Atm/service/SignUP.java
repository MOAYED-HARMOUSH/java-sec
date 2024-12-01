package Atm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SignUP {
    public void signUp(PrintWriter writer, BufferedReader reader) throws IOException {
        String username = null, password = null, balance = null;

        // قراءة البيانات من المستخدم
        writer.println("Enter your username: ");
        username = reader.readLine();
        System.out.println("Received from client: " + username);

        writer.println("Enter your password: ");
        password = reader.readLine();
        System.out.println("Received from client: " + password);

        writer.println("Enter your balance: ");
        balance = reader.readLine();
        System.out.println("Received from client: " + balance);

        // استدعاء خدمة المستخدم للتسجيل
        UserService userService = new UserService();
        boolean isSignUpSuccessful = userService.signUp(username, password, balance);

        // إرسال نتيجة العملية
        if (isSignUpSuccessful) {
            System.out.println("SignUp successful! Welcome, " + username);
            writer.println("SignUp successful! Welcome, " + username);
        } else {
            System.out.println("SignUp failed. Please try again.");
            writer.println("SignUp failed. Please try again.");
        }
    }

}
