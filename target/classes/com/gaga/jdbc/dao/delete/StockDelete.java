package com.gaga.jdbc.dao.delete;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.pojo.StockDayDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockDelete {
    private StockDayDO stock;
    private DataSource dataSource;
    private String sql;
    private Connection conn;
    private PreparedStatement predStmt;

    public StockDelete(StockDayDO stock) throws IOException {
        this.stock = stock;

        dataSource = DBConnectionPool.getDataSource();
    }

    // sDate format is "yyyyMMdd"
    public boolean delete(Date date) throws SQLException {
        boolean isSuccess;

        sql = "delete from stock_days where stockno=? and day=? ";
        try{
            conn = dataSource.getConnection();
            predStmt = conn.prepareStatement(sql);
            predStmt.setBigDecimal(1, stock.getStockNo());
            predStmt.setDate(2, date);

            predStmt.execute();

            isSuccess =true;
            conn.commit();
        } catch (SQLException e) {
            if(conn != null) conn.rollback();
            throw e;
        } finally {
            if(predStmt != null){
                predStmt.clearParameters();
                predStmt.close();
            }
            if(conn != null) conn.close();
        }
        return isSuccess;
    }
}
