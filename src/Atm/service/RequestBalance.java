package Atm.service;

import Atm.session.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestBalance {

    public void getBalance(PrintWriter writer, BufferedReader reader) throws IOException {

        String sessionID;

        writer.println("Enter your sessionID: ");
        sessionID = reader.readLine();
        if (sessionID == null || sessionID.isEmpty()) {
            writer.println("Invalid sessionID. Operation aborted.");
            return;
        }
        //String sessionId = reader.readLine(); // المستخدم يرسل معرف الجلسة
        Integer userId = SessionManager.getUserId(sessionID);
            System.out.println("userId after check session"+userId);
        if (userId != null) {
            // الجلسة صالحة
            writer.println("session successful Now you will get your balance" );

        } else {
            writer.println("Invalid session. Please log in first.");
        }
    }


}
