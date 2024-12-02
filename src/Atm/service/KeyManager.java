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
    public Keys[] generateServerKeys() throws Exception {

        KeyPair serverKeyPair = generateKeyPair();
        PublicKey serverPublicKey = serverKeyPair.getPublic();
        PrivateKey serverPrivateKey = serverKeyPair.getPrivate();

        System.out.println("Server Public Key: " + serverPublicKey);
        System.out.println("Server Private Key: " + serverPrivateKey);

        Keys serverKeys = new Keys(serverPublicKey, serverPrivateKey);
        return new Keys[] { serverKeys };
    }

    public Keys[] generateClientKeys() throws Exception {
        KeyPair clientKeyPair = generateKeyPair();
        PublicKey clientPublicKey = clientKeyPair.getPublic();
        PrivateKey clientPrivateKey = clientKeyPair.getPrivate();

        System.out.println("Client Public Key: " + clientPublicKey);
        System.out.println("Client Private Key: " + clientPrivateKey);

         Keys clientKeys = new Keys(clientPublicKey, clientPrivateKey);
        return new Keys[] { clientKeys };
    }


//    public static void main(String[] args) throws Exception {
//        KeyManager keyManager = new KeyManager();
//        Keys[] keys = keyManager.generateKeys();
//
//        System.out.println("Server Keys: " + keys[0]);
//        System.out.println("Client Keys: " + keys[1]);
//    }
}
