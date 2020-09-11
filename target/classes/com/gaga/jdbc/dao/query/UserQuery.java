package com.gaga.jdbc.dao.query;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.pojo.UserDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserQuery {
    private boolean isAdmin;
    private String sql;
    private Connection conn;
    private DataSource dataSource;

    public UserQuery() throws IOException {
        dataSource = DBConnectionPool.getDataSource();
    }
    // getlists
    public List<UserDO> listUsers() throws IOException, SQLException {
        conn = dataSource.getConnection();
        sql = "select * from account";
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);

//        Map<String, UserDO> userMap = new HashMap<>();
        List<UserDO> list = new ArrayList<>();

        while(resultSet.next()){
            UserDO user = new UserDO();
            user.setUserName(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setFavorite1(resultSet.getBigDecimal("favorite1"));
            user.setFavorite2(resultSet.getBigDecimal("favorite2"));
            user.setFavorite3(resultSet.getBigDecimal("favorite3"));
            user.setFavorite4(resultSet.getBigDecimal("favorite4"));
            user.setFavorite5(resultSet.getBigDecimal("favorite5"));
            user.setRegister(resultSet.getDate("register"));
            user.setLastDate(resultSet.getDate("last_update"));
            boolean isadmin = BigDecimal.ONE.equals(resultSet.getBigDecimal("isadmin"));
            user.setAdmin(isadmin);
//            userMap.put(user.getUserName(), user);
            list.add(user);
        }
        resultSet.close();
        stmt.close();
        conn.close();
        return list;
    }
    // isExist
    public boolean isUserExist(UserDO user) throws IOException, SQLException {
        boolean isExist = false;
        String userName = user.getUserName().toUpperCase();

        conn = dataSource.getConnection();
        sql = "select username from account where username=?";
        PreparedStatement predStmt = conn.prepareStatement(sql);
        predStmt.setString(1, userName);

        ResultSet resultSet = predStmt.executeQuery();
        if(resultSet.next()){
            isExist = true;
        }
        predStmt.clearParameters();
        predStmt.close();
        conn.close();
        return isExist;
    }

    public boolean isAdmin(String userName, String password) throws IOException, SQLException {
        this.isAdmin = false;

        conn = dataSource.getConnection();
        sql = "select isadmin form account where username=?and password=?";
        PreparedStatement predStmt = conn.prepareStatement(sql);
        predStmt.setString(1, userName);
        predStmt.setString(2, password);

        ResultSet resultSet = predStmt.executeQuery();
        if(resultSet.next()){
            BigDecimal isadmin = resultSet.getBigDecimal("isadmin");
            if(isadmin.compareTo(BigDecimal.ONE) == 0) this.isAdmin = true;
        }
        predStmt.clearParameters();
        predStmt.close();
        conn.close();
        return this.isAdmin;
    }
    // get
    public UserDO query(String userName) throws IOException, SQLException {
        sql = "select * from account where username=?";

        conn = dataSource.getConnection();
        PreparedStatement predStmt = conn.prepareStatement(sql);
        predStmt.setString(1, userName.toUpperCase());
        ResultSet rs = predStmt.executeQuery();
        UserDO userDO = new UserDO();
        if(rs.next()){
            userDO.setUserName(rs.getString("username"));
            userDO.setPassword(rs.getString("password"));
            boolean isadmin = BigDecimal.ONE.equals(rs.getBigDecimal("isadmin"));
            userDO.setAdmin(isadmin);
            userDO.setFavorite1(rs.getBigDecimal("favorite1"));
            userDO.setFavorite2(rs.getBigDecimal("favorite2"));
            userDO.setFavorite3(rs.getBigDecimal("favorite3"));
            userDO.setFavorite4(rs.getBigDecimal("favorite4"));
            userDO.setFavorite5(rs.getBigDecimal("favorite5"));
            userDO.setRegister(rs.getDate("register"));
            userDO.setLastDate(rs.getDate("last_update"));
        }
        predStmt.clearParameters();
        predStmt.close();
        conn.close();
        return userDO;

    }

}
