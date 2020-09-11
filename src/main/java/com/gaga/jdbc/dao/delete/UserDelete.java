package com.gaga.jdbc.dao.delete;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.pojo.UserDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDelete {
    private UserDO user;
    private DataSource dataSource;
    private String sql;
    private Connection conn;
    private PreparedStatement predStmt;

    public UserDelete(UserDO user) throws IOException {
        this.user = user;
        dataSource = DBConnectionPool.getDataSource();
    }

    public boolean delete() throws SQLException {
        boolean isSuccess = false;
        sql = "delete from account where username=?";
        try {
            predStmt = conn.prepareStatement(sql);
            predStmt.setString(1, user.getUserName());

            predStmt.execute();
            isSuccess = true;
            conn.commit();
        }catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        }finally {
            if(predStmt != null){
                predStmt.clearParameters();
                predStmt.close();
            }
            if(conn != null){
                conn.close();
            }
        }
        return isSuccess;
    }
}
