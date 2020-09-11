package com.gaga.jdbc.dao.InitTable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaga.jdbc.connections.DBConnectionPool;
import org.apache.commons.dbcp2.BasicDataSource;
import com.gaga.jdbc.pojo.StockTotalNoDO;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateAllStockTable implements CreateTable{
    private String sql;
    private String jsonPath;
    private String tableName;
    private DataSource dataSource;
    private Connection conn;

    public CreateAllStockTable() throws IOException, NamingException {
        tableName = "stock_total_no".toUpperCase();
        jsonPath = "resources/STOCK_TOTAL_NO.json";
        dataSource = DBConnectionPool.getDataSource();
    }

    @Override
    public boolean init() throws IOException, SQLException {
        boolean t = false;
        try {
            if (isExist(this.tableName)) {
                truncateTable(this.tableName);
            }else{
                createTable();
            }
            insertData();
            t = true;
        }catch (IOException | SQLException e){
            truncateTable(this.tableName);
            throw e;
        }
        return t;
    }

    @Override
    public void createTable() throws SQLException {
        sql = "create table "+this.tableName+"(stockno number(10) not null constraint TABLE_NAME_PK primary key, name varchar2(255), code_isin varchar2(255), date_listed date, industrial_group varchar2(255))";

        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);

        }catch (SQLException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(stmt != null) stmt.close();

            if(conn != null) conn.close();
        }

    }

    private int insertData() throws IOException, SQLException {

        int insertDataCount;

        sql = "insert into "+this.tableName+"(stockno, name, code_isin, date_listed, industrial_group) values(?, ?, ?, ?, ?)";
        PreparedStatement predStmt = null;
        try {
            List<StockTotalNoDO> list = readJsonDate();
            conn = dataSource.getConnection();
            predStmt = conn.prepareStatement(sql);
            Iterator<StockTotalNoDO> iter = list.iterator();
            while(iter.hasNext()){
                StockTotalNoDO next = iter.next();
                predStmt.setBigDecimal(1, next.getStockNo());
                predStmt.setString(2, next.getName());
                predStmt.setString(3, next.getCodeISIN());
                predStmt.setDate(4, next.getDataListed());
                predStmt.setString(5, next.getGroup());

                predStmt.addBatch();
                predStmt.clearParameters();
            }
            int[] executeBatch = predStmt.executeBatch();
            insertDataCount = executeBatch.length;

            conn.commit();
        }catch (SQLException e){
            if(conn != null) conn.rollback();
            throw e;
        }finally {
            if(predStmt != null) {
                predStmt.clearParameters();
                predStmt.clearBatch();
                predStmt.close();
            }
            if(conn != null) conn.close();
        }
        return insertDataCount;
    }

    private List<StockTotalNoDO> readJsonDate() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(jsonPath));
        StringBuffer stringBuffer = new StringBuffer();
        String tmp;
        while((tmp = reader.readLine()) != null){
            stringBuffer.append(tmp);
        }
        JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
        JSONArray data = jsonObject.getJSONArray("data");

        List<StockTotalNoDO> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONArray jsonArray = data.getJSONArray(i);
            StockTotalNoDO stockTotalNo = new StockTotalNoDO();

            stockTotalNo.setStockNo(new BigDecimal(jsonArray.getString(0)));
            stockTotalNo.setName(jsonArray.getString(1));
            stockTotalNo.setCodeISIN(jsonArray.getString(2));
            stockTotalNo.setDataListed(Date.valueOf(jsonArray.getString(3).replace("/", "-")));
            stockTotalNo.setGroup(jsonArray.getString(5));

            list.add(stockTotalNo);
        }
        return list;
    }

    public boolean isExist() throws IOException, SQLException {
        return isExist(this.tableName);
    }
}
