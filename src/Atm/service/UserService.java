package Atm.service;

import Atm.dao.UserDao;
import Atm.model.User;

import java.sql.SQLException;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

     public boolean authenticate(String username, String password) {
        try {
             User user = userDao.getUserByUsernameAndPassword(username, password);
            return user != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
     }

    public boolean authenticateWithKey(String username, String password,String Key) {
        try {
            User user = userDao.getUserByUsernameAndPasswordAndKey(username, password,Key);
            return user != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean signUp(String username, String password,String balane) {
        try {
            return userDao.addUser(username, password,balane);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
