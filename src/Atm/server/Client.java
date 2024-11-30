package Atm.server;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3000);  // الاتصال بالسيرفر المحلي على المنفذ 3000
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

             System.out.println(reader.readLine());
            System.out.println(reader.readLine());

              String username = consoleReader.readLine();
            writer.println(username);

             System.out.println(reader.readLine());

              String password = consoleReader.readLine();
            writer.println(password);

            String authResult = reader.readLine();
            System.out.println(authResult);
            String s = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
