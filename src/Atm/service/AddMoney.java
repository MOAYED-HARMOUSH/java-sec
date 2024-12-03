package Atm.service;

import Atm.dao.UserDao;
import Atm.session.SessionManager;
import Atm.utils.RSAEncryptionUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

public class AddMoney {

    public void AddMoney(PrintWriter writer, BufferedReader reader, PublicKey clientPublicKey, PrivateKey serverPrivateKey) throws Exception {
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
            String message = "Session valid please add your amount  ";

            byte[] encryptedChoice = RSAEncryptionUtil.encryptData(message.getBytes(), clientPublicKey);
            writer.println( "ENCForAddMoney|" +java.util.Base64.getEncoder().encodeToString(encryptedChoice));

            String encryptedAmountString = reader.readLine();
            byte[] encryptedAmount= Base64.getDecoder().decode(encryptedAmountString);
            String decryptedChoice = RSAEncryptionUtil.decryptData(encryptedAmount, serverPrivateKey);
            System.out.println("Decrypted Amount: " + decryptedChoice);


//
//            byte[] encryptedBalance = RSAEncryptionUtil.encryptData(message.getBytes(), clientPublicKey);
//
//            writer.println(Arrays.toString(encryptedBalance));
        } else {
            writer.println("Invalid session. Please log in first.");
        }
    }


}
