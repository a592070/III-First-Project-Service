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
//        dataSource = new BasicDataSource();
//        readProperties();
//        setPool();
    }
    public void readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("/db.properties"));
        sConnInfo = properties.getProperty("ConnInfo");
        sUser = properties.getProperty("User");
        sPassword = properties.getProperty("Password");
        sInitialSize = Integer.parseInt(properties.getProperty("InitialSize"));
        sMaxTotal = Integer.parseInt(properties.getProperty("MaxTotal"));
        sMaxIdle = Integer.parseInt(properties.getProperty("MaxIdle"));
        sMaxWait = Integer.parseInt(properties.getProperty("MaxWait"));
        sRemoveAbandonedTimeout = Integer.parseInt(properties.getProperty("RemoveAbandonedTimeout"));
        sAis_batch_num = Integer.parseInt(properties.getProperty("ais_batch_num"));
        sAutoCommit = properties.getProperty("AutoCommit");
    }
    public void setPool(){
        dataSource.setUrl(sConnInfo);
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUsername(sUser);
        dataSource.setPassword(sPassword);
        dataSource.setInitialSize(sInitialSize);    // 初始連線數量
        dataSource.setMaxTotal(sMaxTotal);          // 最大連線數量
        dataSource.setMaxIdle(sMaxIdle);            // 最大空閒連線數量
        dataSource.setMaxWaitMillis(sMaxWait);      // 最大等待時間
        dataSource.setRemoveAbandonedTimeout(sRemoveAbandonedTimeout);  // 回收時間

        dataSource.setDefaultAutoCommit(false);

    }
    public static DataSource getDataSource() throws IOException {
        if(dataSource1 == null) {
            new DBConnectionPool().init();
        }
        return dataSource1;
    }

}
