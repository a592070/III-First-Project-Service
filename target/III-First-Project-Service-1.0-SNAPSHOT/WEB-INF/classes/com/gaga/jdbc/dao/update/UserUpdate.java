package com.gaga.jdbc.dao.update;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.pojo.UserDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class UserUpdate {
    public UserDO user;
    private boolean isAdmin;
    private String sql;
    private Connection conn;
    private DataSource dataSource;

    public UserUpdate(UserDO user) throws IOException {
        this.user = user;
        dataSource = DBConnectionPool.getDataSource();
    }

    public boolean update() throws SQLException {
        boolean isSuccess = false;
        sql = "update account set password=? favorite1=? favorite2=? favorite3=? favorite4=? favorite5=? last_update=? where username=?";
        PreparedStatement predStmt = null;
        try {
            conn = dataSource.getConnection();
            predStmt = conn.prepareStatement(sql);

            predStmt.setString(1, user.getPassword());
            predStmt.setBigDecimal(2, user.getFavorite1());
            predStmt.setBigDecimal(3, user.getFavorite2());
            predStmt.setBigDecimal(4, user.getFavorite3());
            predStmt.setBigDecimal(5, user.getFavorite4());
            predStmt.setBigDecimal(6, user.getFavorite5());
            predStmt.setDate(7, Date.valueOf(LocalDate.now()));
            predStmt.setString(8, user.getUserName());

            predStmt.executeUpdate();

            conn.commit();
            isSuccess = true;
        }catch (SQLException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(predStmt != null){
                predStmt.clearParameters();
                predStmt.close();
            }
            if(conn != null) conn.close();
        }
        return isSuccess;
    }
}
