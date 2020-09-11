package com.gaga.jdbc.dao.InitTable;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.dao.insert.StockInsert;
import com.gaga.jdbc.pojo.StockDayDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateStockDays implements CreateTable{
    private StockDayDO stock;
    private String sStockNo;
    private String tableName;
    private String[] columnName;
    private String sql;
    private DataSource dataSource;
    private Connection conn;


    public CreateStockDays() throws IOException {
        this.tableName = "stock_days".toUpperCase();
        dataSource = DBConnectionPool.getDataSource();
    }

    @Override
    public boolean init() throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {
        boolean t = false;
        try {
            if (isExist(this.tableName)) {
                truncateTable(this.tableName);
            }else{
                createTable();
            }
            new StockInsert(stock).insert();
            t = true;
        }catch (IOException | SQLException | KeyManagementException | NoSuchAlgorithmException e){
            truncateTable(this.tableName);
            throw e;
        }
        return t;
    }

    @Override
    public void createTable() throws SQLException {
        sql = "create table "+this.tableName+"(stockno number(10) not null , trade_volume numeric(20), transation numeric(10), h_price numeric(10,2), l_price numeric(10,2), opening_price numeric(10,2), closing_price numeric(10,2), day date)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            conn.createStatement().execute(sql);

            conn.commit();
        }catch (SQLException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(conn != null) conn.close();
        }
    }

    public boolean isExist() throws IOException, SQLException {
        return isExist(this.tableName);
    }
}
