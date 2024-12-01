package Atm.server;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

             String serverMessage;
            while ((serverMessage = reader.readLine()) != null) {
                System.out.println(serverMessage); // طباعة الرسالة من السيرفر
                if (serverMessage.contains("Please enter your choice")) {
                    break; // إذا طلب إدخال خيار، نخرج من الحلقة
                }
            }

            // إدخال اختيار المستخدم (1 أو 2)
            String choice = consoleReader.readLine();
            writer.println(choice); // إرسال الاختيار للسيرفر

            // متابعة العملية بناءً على اختيار المستخدم
            while ((serverMessage = reader.readLine()) != null) {
                System.out.println(serverMessage);
                if (serverMessage.contains("Enter your username:") || serverMessage.contains("Enter your password:")|| serverMessage.contains("Enter your Key:") || serverMessage.contains("Enter your balance:")) {
                    String input = consoleReader.readLine(); // إدخال المستخدم
                    writer.println(input); // إرسال الإدخال للسيرفر
                }
                if (serverMessage.contains("successful") || serverMessage.contains("Invalid")) {
               //12     String input = consoleReader.readLine(); // إدخال المستخدم
                  //  System.out.println(serverMessage);
                    break; // إنهاء بعد ظهور نتيجة العملية
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
