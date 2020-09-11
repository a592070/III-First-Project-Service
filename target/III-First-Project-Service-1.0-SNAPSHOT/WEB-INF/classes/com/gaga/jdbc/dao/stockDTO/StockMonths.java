package com.gaga.jdbc.dao.stockDTO;

public class StockMonths {
    private String sUrl1 = "https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json&";
    private String sUrl2 = "date=";
    private String sUrl3 = "&stockNo=";
    private String sUrl;
    public StockMonths(String stockId, String month) {
        this.sUrl = sUrl1+sUrl2+month+sUrl3+stockId;
    }

}
