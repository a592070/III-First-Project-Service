package com.gaga.jdbc.connections;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DBConnectionPool {
    private static BasicDataSource dataSource;
    private static DataSource dataSource1;

    private String sConnInfo;
    private String sUser;
    private String sPassword;
    private int sInitialSize;
    private int sMaxTotal;
    private int sMaxIdle;
    private int sMaxWait;
    private int sRemoveAbandonedTimeout;
    private int sAis_batch_num;
    private String sAutoCommit;

    private DBConnectionPool() throws IOException {
    }

    public void init() throws IOException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource1 = (DataSource) envContext.lookup("com.gaga.jdbc.connections.DBConnectionPool");
        }catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() throws IOException {
        if(dataSource1 == null) {
            new DBConnectionPool().init();
        }
        return dataSource1;
    }

}
