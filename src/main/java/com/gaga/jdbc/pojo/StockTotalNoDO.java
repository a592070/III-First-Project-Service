package com.gaga.jdbc.pojo;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StockTotalNoDO {
    /**
     * stockNo, name, ISIN_Code, Date_Listed, Industrial_Group
     */
    private BigDecimal stockNo;
    private String name;
    private String codeISIN;
    private Date dataListed;
    private String group;

    public StockTotalNoDO(BigDecimal stockNo, String name, String codeISIN, Date dataListed, String group) {
        this.stockNo = stockNo;
        this.name = name;
        this.codeISIN = codeISIN;
        this.dataListed = dataListed;
        this.group = group;
    }

    public StockTotalNoDO() {
    }

    public BigDecimal getStockNo() {
        return stockNo;
    }

    public void setStockNo(BigDecimal stockNo) {
        this.stockNo = stockNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeISIN() {
        return codeISIN;
    }

    public void setCodeISIN(String codeISIN) {
        this.codeISIN = codeISIN;
    }

    public Date getDataListed() {
        return dataListed;
    }

    public void setDataListed(Date dataListed) {
        this.dataListed = dataListed;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "StockTotalNoDO{" +
                "id=" + stockNo +
                ", name='" + name + '\'' +
                ", codeISIN='" + codeISIN + '\'' +
                ", dataListed=" + dataListed +
                ", group='" + group + '\'' +
                '}';
    }
    public JSONArray toJsonArray(){
        String data = "[" + "\"" + String.valueOf(stockNo) + "\"" + ","+
                "\"" + name + "\"" +","+
                "\"" + codeISIN + "\"" +","+
                "\"" + String.valueOf(dataListed) + "\"" +","+
                "\"" + group + "\"" + "]";

        return JSONArray.parseArray(data);
    }
}
