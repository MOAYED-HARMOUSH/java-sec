package Atm.service;

import Atm.model.Keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyManager {

    // توليد مفتاح خاص وعام باستخدام RSA
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048); // 2048 bit key size
        return keyPairGen.generateKeyPair();
    }

    // دالة لتوليد المفاتيح
    public Keys[] generateKeys() throws Exception {

        // توليد المفاتيح للسيرفر
        KeyPair serverKeyPair = generateKeyPair();
        PublicKey serverPublicKey = serverKeyPair.getPublic();
        PrivateKey serverPrivateKey = serverKeyPair.getPrivate();

        // توليد المفاتيح للعميل
        KeyPair clientKeyPair = generateKeyPair();
        PublicKey clientPublicKey = clientKeyPair.getPublic();
        PrivateKey clientPrivateKey = clientKeyPair.getPrivate();

        // طباعة المفاتيح
        System.out.println("Server Public Key: " + serverPublicKey);
        System.out.println("Server Private Key: " + serverPrivateKey);
        System.out.println("Client Public Key: " + clientPublicKey);
        System.out.println("Client Private Key: " + clientPrivateKey);

        Keys serverKeys = new Keys(serverPublicKey, serverPrivateKey);
        Keys clientKeys = new Keys(clientPublicKey, clientPrivateKey);
        return new Keys[] { serverKeys, clientKeys };
    }

//    public static void main(String[] args) throws Exception {
//        KeyManager keyManager = new KeyManager();
//        Keys[] keys = keyManager.generateKeys();
//
//        System.out.println("Server Keys: " + keys[0]);
//        System.out.println("Client Keys: " + keys[1]);
//    }
}
