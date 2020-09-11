package com.gaga.jdbc.dao.stockDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaga.jdbc.pojo.StockDayDO;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StockDataSource {
    // https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json&date=20200910&stockNo=2330
//    private String surl = "https://www.twse.com.tw/exchangeReport/STOCK_DAY";
    private String sUrl1 = "https://www.twse.com.tw/en/exchangeReport/STOCK_DAY?response=json";
    private String sUrl2 = "&date=";
    private String sUrl3 = "&stockNo=";
    private String sUrl;
    private String sDate;   // yyyyMMdd
    private String sStockNo;


    public StockDataSource(String sDate, String sStockNo) {
        this.sDate = sDate;
        this.sStockNo = sStockNo;
        sUrl = sUrl1+sUrl2+sDate+sUrl3+sStockNo;
    }
    public List<StockDayDO> getStockDaysList() throws IOException, KeyManagementException, NoSuchAlgorithmException {
        String json = getJson();
        List<List<String>> lists = getJsonList(json);
        List<StockDayDO> beans = new ArrayList<>();

        lists.forEach(ele -> beans.add(getStockDayBean(ele)));
        return beans;
    }
    public String getJson() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SslUtils.ignoreSSL();

        HttpsURLConnection conn = (HttpsURLConnection) new URL(sUrl).openConnection();

        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");

//        conn.setRequestProperty("response", "json");
//        conn.setRequestProperty("date", sDate);
//        conn.setRequestProperty("stockNo", sStockNo);
        conn.setRequestMethod("GET");
        conn.connect();
        if(conn.getResponseCode() == 200){
            BufferedReader bufIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();

            String temp;
            while((temp=bufIn.readLine()) != null){
                stringBuffer.append(temp);
            }

            bufIn.close();
            conn.disconnect();
            return stringBuffer.toString();
        }else{
            conn.disconnect();
            return null;
        }
    }
    public List<List<String>> getJsonList(String data){
        List<String> inList = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();

        JSONObject jsonObject = JSON.parseObject(data);
        JSONArray dataArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < dataArray.size(); i++) {
            JSONArray jsonArray = dataArray.getJSONArray(i);
            list.add(jsonArray.toJavaList(String.class));
        }

        return list;
    }
    public <E> List<E> getFields(String data){
        JSONObject jsonObject = JSON.parseObject("fields");
        JSONArray dataArray = jsonObject.getJSONArray("fields");
        return null;
    }

    /**
     "Date",
     "Trade Volume",
     "Trade Value", //no add
     "Opening Price",
     "Highest Price",
     "Lowest Price",
     "Closing Price",
     "Change",  //no add
     "Transaction"
     * @param list
     * @param <E>
     */
    public <E> StockDayDO getStockDayBean(List<E> list){
//        int size = list.size();
        StockDayDO dayBean = new StockDayDO();
        dayBean.setStockNo(new BigDecimal(this.sStockNo));
        dayBean.setDate(Date.valueOf(String.valueOf(list.get(0)).replace("/", "-")));
        dayBean.setTradeVolume(new BigDecimal(String.valueOf(list.get(1)).replace(",", "")));
        dayBean.setOpeningPrice(new BigDecimal(String.valueOf(list.get(3)).replace(",", "")));
        dayBean.setHighestPrice(new BigDecimal(String.valueOf(list.get(4)).replace(",", "")));
        dayBean.setLowestPrice(new BigDecimal(String.valueOf(list.get(5)).replace(",", "")));
        dayBean.setClosingPrice(new BigDecimal(String.valueOf(list.get(6)).replace(",", "")));
        dayBean.setTransAction(new BigDecimal(String.valueOf(list.get(8)).replace(",", "")));
        return dayBean;
    }


}
