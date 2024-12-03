package Atm.service;

import Atm.dao.UserDao;
import Atm.session.SessionManager;
import Atm.utils.RSAEncryptionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

public class RequestBalance {

    public void getBalance(PrintWriter writer, BufferedReader reader, PublicKey clientPublicKey) throws Exception {
        String sessionID;

        writer.println("Enter your sessionID: ");
        sessionID = reader.readLine();
        if (sessionID == null || sessionID.isEmpty()) {
            writer.println("Invalid sessionID. Operation aborted.");
            return;
        }

        Integer userId = SessionManager.getUserId(sessionID);
        System.out.println("userId after check session: " + userId);

        if (userId != null) {
            // الجلسة صالحة
            UserDao userDao = new UserDao();
            int balance = userDao.getBalance(userId); // حفظ النتيجة في متغير
            String message = "Session valid Now you will get your balance = " + balance;

            byte[] encryptedChoice = RSAEncryptionUtil.encryptData(message.getBytes(), clientPublicKey);
            writer.println( "ENC|" +java.util.Base64.getEncoder().encodeToString(encryptedChoice));
//
//            byte[] encryptedBalance = RSAEncryptionUtil.encryptData(message.getBytes(), clientPublicKey);
//
//            writer.println(Arrays.toString(encryptedBalance));
        } else {
            writer.println("Invalid session. Please log in first.");
        }
    }


}
