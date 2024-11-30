package Atm.model;

import java.util.Arrays;

public class User {
    private int userId;
    private String username;
    private String encryptedPassword;
    private String encryptedBalance;

    // Constructor
    public User(int userId, String username, byte[] encryptedPassword, String encryptedBalance) {
        this.userId = userId;
        this.username = username;
        this.encryptedPassword = Arrays.toString(encryptedPassword);
        this.encryptedBalance = encryptedBalance;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEncryptedBalance() {
        return encryptedBalance;
    }

    public void setEncryptedBalance(String encryptedBalance) {
        this.encryptedBalance = encryptedBalance;
    }
}
