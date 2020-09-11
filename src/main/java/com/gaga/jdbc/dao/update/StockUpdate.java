package com.gaga.jdbc.dao.update;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.pojo.StockDayDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockUpdate {
    private DataSource dataSource;
    private StockDayDO stock;
    private String sql;
    private Connection conn;
    private PreparedStatement predStmt;

    public StockUpdate(StockDayDO stock) throws IOException {
        this.stock = stock;
        dataSource = DBConnectionPool.getDataSource();
    }
    public boolean update() throws SQLException {
        sql = "update stock_days set trade_volume=?, transation=?, h_price=?, l_price=?, opening_price=?, closing_price=? where stockno=?, day=?";
        boolean isSuccess = false;
        try{
            conn = dataSource.getConnection();
            predStmt = conn.prepareStatement(sql);

            predStmt.setBigDecimal(1, stock.getTradeVolume());
            predStmt.setBigDecimal(2, stock.getTransAction());
            predStmt.setBigDecimal(3, stock.getHighestPrice());
            predStmt.setBigDecimal(4, stock.getLowestPrice());
            predStmt.setBigDecimal(5, stock.getOpeningPrice());
            predStmt.setBigDecimal(6, stock.getClosingPrice());
            predStmt.setBigDecimal(7, stock.getStockNo());
            predStmt.setDate(8, stock.getDate());

            isSuccess = predStmt.execute();

            conn.commit();
        } catch (SQLException e) {
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
