package Atm.server;

import Atm.model.Keys;
import Atm.service.KeyManager;
import Atm.utils.RSAEncryptionUtil;

import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class Client {
    private static PublicKey serverPublicKey; // تخزين المفتاح العام للسيرفر هنا

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {


            KeyManager keysGenerate =new KeyManager();
            Keys[] keys = keysGenerate.generateClientKeys();


            PublicKey clientPublicKey = keys[0].getPublicKey();
            PrivateKey clientPrivateKey = keys[0].getPrivateKey();

            String base64PublicKey = java.util.Base64.getEncoder().encodeToString(clientPublicKey.getEncoded());
            writer.println(base64PublicKey);


            String serverMessage;

            while ((serverMessage = reader.readLine()) != null) {
                System.out.println(serverMessage);
                if (serverMessage.contains("Server Public Key:")) {
                    String publicKeyString = serverMessage.split(":")[1].trim(); // استخراج المفتاح العام من الرسالة
                    if (publicKeyString.startsWith("-----BEGIN PUBLIC KEY-----")) {
                        publicKeyString = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n", "");
                    }
                    serverPublicKey = convertStringToPublicKey(publicKeyString); // تحويل المفتاح من String إلى PublicKey
                }
                if (serverMessage.contains("Please enter your choice")) {
                    break;
                }
            }

            String choice = consoleReader.readLine();
            byte[] encryptedChoice = RSAEncryptionUtil.encryptData(choice.getBytes(), serverPublicKey);
            writer.println(java.util.Base64.getEncoder().encodeToString(encryptedChoice));

            //writer.println(choice);

            while ((serverMessage = reader.readLine()) != null) {


                if (serverMessage.contains("Enter your username:") || serverMessage.contains("Enter your password:") ||
                        serverMessage.contains("Enter your Key:") || serverMessage.contains("Enter your balance:") ||
                        serverMessage.contains("Enter your sessionID: ") ) {
                     System.out.println(serverMessage);

                    String input = consoleReader.readLine();

                    writer.println(input);

                }


                if (serverMessage.contains("successful") || serverMessage.contains("Invalid")|| serverMessage.contains("Your session ID is")) {
                    System.out.println(serverMessage);

                    break;
                }

                if (serverMessage.startsWith("ENC|")) {
                    String encryptedPart = serverMessage.substring(4); // إزالة "ENC|"


                byte[] encryptedBalanceBytes = Base64.getDecoder().decode(encryptedPart);

                // فك تشفير الرصيد باستخدام المفتاح الخاص للعميل

                String decryptedBalance = RSAEncryptionUtil.decryptData(encryptedBalanceBytes, clientPrivateKey);

                System.out.println(decryptedBalance);
                }
                if (serverMessage.startsWith("ENCForAddMoney|")) {
                    String encryptedPart = serverMessage.substring(15); // إزالة "ENC|"


                    byte[] encryptedBalanceBytes = Base64.getDecoder().decode(encryptedPart);


                    String decryptedBalance = RSAEncryptionUtil.decryptData(encryptedBalanceBytes, clientPrivateKey);

                    System.out.println(decryptedBalance);
                    String money = consoleReader.readLine();
                    byte[] Encrptedmoney = RSAEncryptionUtil.encryptData(money.getBytes(), serverPublicKey);
                    writer.println(java.util.Base64.getEncoder().encodeToString(Encrptedmoney));

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // دالة لتحويل المفتاح العام من String إلى PublicKey
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
