package Atm.model;

import java.security.PrivateKey;
import java.security.PublicKey;

// فئة لتخزين المفاتيح العامة والخاصة
public class Keys {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public Keys(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "PublicKey: " + publicKey + ", PrivateKey: " + privateKey;
    }
}
