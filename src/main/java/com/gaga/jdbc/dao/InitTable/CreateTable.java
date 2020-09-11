package com.gaga.jdbc.dao.InitTable;

import com.gaga.jdbc.connections.DBConnectionPool;

import javax.naming.NamingException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public interface CreateTable {

    void createTable() throws IOException, SQLException;
    boolean init() throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException;

    default boolean isExist(String tableName) throws IOException, SQLException {
        boolean t = false;
        String query = "select table_name from user_tables where table_name = ?";

        Connection conn = null;
        PreparedStatement predStmt = null;
        try {
            conn = DBConnectionPool.getDataSource().getConnection();

            predStmt = conn.prepareStatement(query);
            predStmt.setString(1, tableName);
            ResultSet rs = predStmt.executeQuery();
            if (rs.next()) t = true;

        }catch (IOException | SQLException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(predStmt != null) {
                predStmt.clearParameters();
                predStmt.close();
            }
            if(conn != null) conn.close();
        }
        return t;
    }
    default void deleteTable(String tableName) throws IOException, SQLException {
        String sql = "delete from "+tableName;
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DBConnectionPool.getDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
        }catch (IOException | SQLException e){
            throw e;
        }finally {
            if(stmt != null) {
                stmt.close();
            }
            if(conn != null) conn.close();
        }
    }
    default void dropTable(String tableName) throws IOException, SQLException {
        String sql = "drop table "+tableName+" cascade constraints";
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DBConnectionPool.getDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
        }catch (IOException | SQLException e){
            throw e;
        }finally {
            if(stmt != null) {
                stmt.close();
            }
            if(conn != null) conn.close();
        }
    }
    default void truncateTable(String tableName)throws IOException, SQLException {
        String sql = "truncate table "+tableName;
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DBConnectionPool.getDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
        }catch (IOException | SQLException e){
            throw e;
        }finally {
            if(stmt != null) {
                stmt.close();
            }
            if(conn != null) conn.close();
        }
    }
}
