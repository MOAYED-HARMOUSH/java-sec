package Atm.server;

import Atm.model.Keys;
import Atm.service.*;
import Atm.utils.RSAEncryptionUtil;

import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class ServerHandler implements Runnable {
    private Socket clientSocket;
    private static PublicKey GeneralclientPublicKey; // تخزين المفتاح العام للسيرفر هنا


    public ServerHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            KeyManager keysGenerate =new KeyManager();
            Keys[] keys = keysGenerate.generateServerKeys();

            // إرسال المفتاح العام للسيرفر إلى العميل
            PublicKey serverPublicKey = keys[0].getPublicKey();
            PrivateKey serverPrivateKey = keys[0].getPrivateKey();

            String base64PublicKey = java.util.Base64.getEncoder().encodeToString(serverPublicKey.getEncoded());

            String clientPublicKey = reader.readLine();
            if (clientPublicKey == null) {
                writer.println("Invalid clientPublicKey. Connection closing.");
                return;
            }
            String publicKeyString = clientPublicKey;
            if (publicKeyString.startsWith("-----BEGIN PUBLIC KEY-----")) {
                publicKeyString = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n", "");
            }
            GeneralclientPublicKey = convertStringToPublicKey(publicKeyString); // تحويل المفتاح من String إلى PublicKey
            System.out.println("GeneralclientPublicKey ="+GeneralclientPublicKey);

            // إرسال رسالة الاختيار إلى العميل
            writer.println("Welcome to the ATM!");
            writer.println("Server Public Key: " + base64PublicKey); // إرسال المفتاح العام بصيغة Base64
            writer.println("To login, enter 1.");
            writer.println("To sign up, enter 2.");
            writer.println("To login with key, enter 3.");
            writer.println("To Get Your Balance enter 4.");

            writer.println("Please enter your choice: ");

            // قراءة اختيار المستخدم
            String encryptedChoiceString = reader.readLine();
            byte[] encryptedChoice = Base64.getDecoder().decode(encryptedChoiceString);
            String decryptedChoice = RSAEncryptionUtil.decryptData(encryptedChoice, serverPrivateKey);

            System.out.println("Decrypted choice: " + decryptedChoice);
            if (decryptedChoice == null) {
                writer.println("Invalid input. Connection closing.");
                return;
            }

            int choice;
            try {
                choice = Integer.parseInt(decryptedChoice);
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
                case 3:
                    LoginWithKey loginWithKey = new LoginWithKey();
                    loginWithKey.LoginWithKey(writer, reader);
                    break;

                case 4:
                    RequestBalance requestBalance = new RequestBalance();
                    requestBalance.getBalance(writer, reader);
                    break;

                default:
                    writer.println("Invalid choice. Please restart and try again.");
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static PublicKey convertStringToPublicKey(String publicKeyString) {
        try {
            // هنا نقوم بتحويل المفتاح باستخدام Base64 أو طريقة معتمدة
            // هذه العملية تعتمد على كيفية إرسال المفتاح، إن كان مشفرًا باستخدام Base64 أو غيره
            // كمثال:
            byte[] encodedKey = java.util.Base64.getDecoder().decode(publicKeyString); // فك ترميز Base64
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
            java.security.spec.X509EncodedKeySpec keySpec = new java.security.spec.X509EncodedKeySpec(encodedKey);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
