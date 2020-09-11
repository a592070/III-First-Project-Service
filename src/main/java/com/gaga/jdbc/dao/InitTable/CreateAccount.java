package com.gaga.jdbc.dao.InitTable;

import com.gaga.jdbc.connections.DBConnectionPool;
import com.gaga.jdbc.dao.insert.UserInsert;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateAccount implements CreateTable{
    private String tableName;
    private String sql;
    private Connection conn;
    private DataSource dataSource;


    public CreateAccount() throws IOException {
        this.tableName = "account".toUpperCase();
        this.sql = "create table "+this.tableName+"(username varchar2(255) not null constraint ACCOUNT_PK primary key, password varchar2(255), isadmin number(1) default 0 not null, favorite1 number(10), favorite2 number(10), favorite3 number(10), favorite4 number(10), favorite5 number(10) , register date, last_update date)";
        dataSource = DBConnectionPool.getDataSource();
    }

    @Override
    public boolean init() throws IOException, SQLException {
        boolean t = false;
        try{
            if (isExist(this.tableName)) {
                truncateTable(this.tableName);
            }else {
                createTable();
            }
            new UserInsert().insertAdmin();
            t = true;
        }catch (IOException | SQLException e){
            truncateTable(this.tableName);
            throw e;
        }
        return t;
    }

    @Override
    public void createTable() throws IOException, SQLException {
        conn = dataSource.getConnection();
        conn.createStatement().execute(sql);

        conn.close();
    }
    public boolean isExist() throws IOException, SQLException {
        return isExist(this.tableName);
    }

}
