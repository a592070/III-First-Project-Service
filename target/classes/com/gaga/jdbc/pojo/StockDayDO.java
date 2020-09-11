package com.gaga.jdbc.pojo;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StockDayDO {
    private String name;
    private BigDecimal stockNo;
    private Date date;
    private BigDecimal tradeVolume;     // 成交股數
    private BigDecimal transAction;     // 成交筆數
    private BigDecimal highestPrice;    // 最高價
    private BigDecimal lowestPrice;     // 最低價
    private BigDecimal openingPrice;    // 開盤價
    private BigDecimal closingPrice;    // 收盤價

    public StockDayDO() {
    }

    public StockDayDO(String name, BigDecimal id, Date date, BigDecimal tradeVolume, BigDecimal transAction, BigDecimal highestPrice, BigDecimal lowestPrice, BigDecimal openingPrice, BigDecimal closingPrice) {
        this.name = name;
        this.stockNo = id;
        this.date = date;
        this.tradeVolume = tradeVolume;
        this.transAction = transAction;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getStockNo() {
        return stockNo;
    }

    public void setStockNo(BigDecimal stockNo) {
        this.stockNo = stockNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(BigDecimal tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public BigDecimal getTransAction() {
        return transAction;
    }

    public void setTransAction(BigDecimal transAction) {
        this.transAction = transAction;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    @Override
    public String toString() {
        return "[" +
                "name='" + name + '\'' +
                ", id=" + stockNo +
                ", date=" + date +
                ", tradeVolume=" + tradeVolume +
                ", transAction=" + transAction +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                ']';
    }
    public JSONArray toJsonArray(){
        String data = "[" + "\"" + name + "\"" + ","+
                "\"" + String.valueOf(stockNo) + "\"" +","+
                "\"" + String.valueOf(date) + "\"" +","+
                "\"" + String.valueOf(tradeVolume) + "\"" +","+
                "\"" + String.valueOf(transAction) + "\"" +","+
                "\"" + String.valueOf(highestPrice) + "\"" +","+
                "\"" + String.valueOf(lowestPrice) + "\"" +","+
                "\"" + String.valueOf(openingPrice) + "\"" +","+
                "\"" + String.valueOf(closingPrice) + "\"" +"]";

        return JSONArray.parseArray(data);
    }
}
