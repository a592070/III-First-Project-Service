package com.gaga.jdbc.service;

import com.gaga.jdbc.dao.UserDAOImpl;
import com.gaga.jdbc.dao.delete.UserDelete;
import com.gaga.jdbc.dao.query.UserQuery;
import com.gaga.jdbc.pojo.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginService {
    private String userName;
    private String password;
    private UserDO user;
    private static List<UserDO> list;

    public LoginService(String userName , String password) throws IOException, SQLException {
        this.userName = userName.toUpperCase();
        this.password = password;
        user = new UserDO(this.userName, this.password);
        if(list == null)getList();
    }


    public LoginService() throws IOException, SQLException {
        this("guest" , "guest");
    }

    public static List<UserDO> getList() throws IOException, SQLException {
        list = new UserQuery().listUsers();
        return list;
    }

    public boolean login(){
        for (UserDO ele : list) {
            if(userName.equals(ele.getUserName()) && password.equals(ele.getPassword())){
                user = ele;
                return true;
            }
        }
        return false;
    }
    public UserDO getUser(){
        return user;
    }

    public boolean isRegistered(){
        for (UserDO ele : list) {
            if(userName.equals(ele.getUserName())) return true;
        }
        return false;
    }

    public boolean register() throws IOException, SQLException {
        return register(false);
    }
    public boolean register(boolean isAdmin) throws IOException, SQLException {
        if(isAdmin) user.setAdmin(true);
        boolean b = new UserDAOImpl().addElement(user);
        getList();
        return b;
    }

    public boolean isAdmin(){
        return user.isAdmin();
    }

    public boolean updateUser(UserDO user) throws IOException, SQLException {
        boolean b = new UserDAOImpl().setElement(user);
        getList();
        return b;
    }

    public boolean remove() throws IOException, SQLException {
        boolean b = new UserDelete(user).delete();
        getList();
        return b;
    }
}
