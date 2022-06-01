package com.example.demo.Service;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String id, String password){
        System.out.println(id+password);
        return userDao.login(id,password);
    }

    public int register(String id,String name,String password){
        return userDao.register(id, name, password);
    }

    public String phone(String id){
        return userDao.phone(id);
    }

    public int alterPsw(String id,String password){
        return  userDao.alterPsw(id,password);
    }

    public String getUserName(String id){
        return userDao.getUserName(id);
    }
}
