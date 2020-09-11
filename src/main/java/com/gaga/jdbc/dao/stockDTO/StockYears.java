package com.gaga.jdbc.dao.stockDTO;

public class StockYears {
    private String sUrl1 = "https://www.twse.com.tw/exchangeReport/FMNPTK?response=json&";
    private String sUrl2 = "stockNo=";
    private String sUrl;

    public StockYears(String stockId) {
        this.sUrl = sUrl1+sUrl2+stockId;
    }

}
