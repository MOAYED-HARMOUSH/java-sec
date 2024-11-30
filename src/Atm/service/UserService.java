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
        }
    }
}
