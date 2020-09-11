package com.gaga.jdbc.dao.insert;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.dao.stockDTO.StockDataSource;
import com.gaga.jdbc.pojo.StockDayDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StockInsert {
    private StockDayDO stock;
    private String sDate;
    private String sStockNo;
    private String tableName;
    private String sql;

    private DataSource dataSource;
    private Connection conn;

    public StockInsert(StockDayDO stock) throws IOException {
        this(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), stock);
    }

    public StockInsert(String sDate, StockDayDO stock) throws IOException {
        this.sDate = sDate;
        this.stock = stock;
        this.sStockNo = stock.getStockNo().toString();
        this.tableName = "stock_days".toUpperCase();
        this.sql = "insert into stock_days(stockno, trade_volume, transation, h_price, l_price, opening_price, closing_price, day) values(?, ?, ?, ?, ?, ?, ?, ?) where day=?";

        this.dataSource = DBConnectionPool.getDataSource();
    }

    public boolean insert() throws IOException, SQLException, KeyManagementException, NoSuchAlgorithmException {
        return insert(this.sDate);
    }
    public boolean insert(String sDate) throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException {
        boolean isSuccess = false;

        CallableStatement callStmt = null;
        try {
            List<StockDayDO> stockDaysList = new StockDataSource(sDate, sStockNo).getStockDaysList();
            conn = dataSource.getConnection();

            callStmt = conn.prepareCall("{call replace_into(?,?,?,?,?,?,?,?)}");
            for (StockDayDO stockDayDO : stockDaysList) {


                callStmt.setBigDecimal(1, stockDayDO.getStockNo());
                callStmt.setBigDecimal(2, stockDayDO.getTradeVolume());
                callStmt.setBigDecimal(3, stockDayDO.getTransAction());
                callStmt.setBigDecimal(4, stockDayDO.getHighestPrice());
                callStmt.setBigDecimal(5, stockDayDO.getLowestPrice());
                callStmt.setBigDecimal(6, stockDayDO.getOpeningPrice());
                callStmt.setBigDecimal(7, stockDayDO.getClosingPrice());
                callStmt.setDate(8, stockDayDO.getDate());

                callStmt.addBatch();
                callStmt.clearParameters();

            }
            callStmt.executeBatch();

            conn.commit();
            isSuccess = true;
        }catch (IOException | SQLException | KeyManagementException | NoSuchAlgorithmException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(callStmt != null){
                callStmt.clearParameters();
                callStmt.clearBatch();
                callStmt.close();
            }
            if(conn != null) conn.close();
        }
        return isSuccess;
    }
}
