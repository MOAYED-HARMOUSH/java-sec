package Atm.utils;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAEncryptionUtil {

    // دالة لتشفير البيانات باستخدام المفتاح العام
    public static byte[] encryptData(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // دالة لفك تشفير البيانات باستخدام المفتاح الخاص
    public static String decryptData(byte[] encryptedData, PrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        return new String(decryptedBytes);
    }
}
