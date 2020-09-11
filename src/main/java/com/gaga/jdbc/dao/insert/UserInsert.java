package com.gaga.jdbc.dao.insert;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.pojo.UserDO;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class UserInsert {
    private DataSource dataSource;
    private String sql;
    private int insertCount;
    private PreparedStatement predStmt = null;
    private Connection conn = null;

    public UserInsert() throws IOException {
        dataSource = DBConnectionPool.getDataSource();
        sql = "insert into account(username, password, isadmin, register, last_update) values(?, ?, 0, ?, ?)";
    }

    public boolean insertAdmin() throws SQLException, IOException {
        sql = "insert into account(username, password, isadmin, register, last_update) values(?, ?, 1, ?, ?)";

        Properties prop = new Properties();
        prop.load(new FileReader("resources/admin.properties"));
        String admin = prop.getProperty("username").toUpperCase();
        String password = prop.getProperty("password");

        UserDO userAdmin = new UserDO();
        userAdmin.setUserName(admin);
        userAdmin.setPassword(password);
        userAdmin.setAdmin(true);

        return insert(userAdmin);
    }
    public boolean insert(UserDO user) throws SQLException, IOException {
        boolean isInsert = false;
        try {
            conn = dataSource.getConnection();
            predStmt = conn.prepareStatement(sql);
            predStmt.setString(1, user.getUserName());
            predStmt.setString(2, user.getPassword());
            predStmt.setDate(3, new Date(System.currentTimeMillis()));
            predStmt.setDate(4, new Date(System.currentTimeMillis()));

            insertCount = predStmt.executeUpdate();

            isInsert = true;
            conn.commit();
        } catch (SQLException e) {
            if(conn != null) conn.rollback();
            throw e;
        } finally {
            if(predStmt != null) {
                predStmt.clearParameters();
                predStmt.close();
            }
            if(conn != null) conn.close();
        }
        return isInsert;
    }
}
