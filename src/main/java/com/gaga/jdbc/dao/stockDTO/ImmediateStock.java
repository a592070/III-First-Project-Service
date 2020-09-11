package com.gaga.jdbc.dao.stockDTO;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImmediateStock {
    private HttpURLConnection connection;
    private String surl = "https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=";

    public ImmediateStock(String stockId) {
        this.surl = surl+"tse_"+stockId+".tw";
    }

    public void init() throws Exception{
        SslUtils.ignoreSSL();
        URL url = new URL(this.surl);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        System.out.println("Connected status: "+status);
    }
    public void getJsonData() throws Exception {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = reader.readLine())!=null){
            responseContent.append(line);
        }
        reader.close();
//        System.out.println(responseContent.toString());
        analyticJson(responseContent.toString());

    }
    public void analyticJson(String responseBody){
//        JSONObject jsonObject = new JSONObject(responseBody);

//        String tmp = jsonObject.getJSONArray("msgArray").get(0).toString();
        /**將"msgArray"中的object抓出來*/
//        JSONObject jsonObject1 =new JSONObject(tmp);

//        System.out.println(jsonObject.getJSONArray("msgArray").get(0));

        /**"z"的位置就是目前價格 "o"是開盤價格*/
//        System.out.println("目前成交價格:\t"+jsonObject1.get("z"));

//        Double nowPrice = Double.valueOf(jsonObject1.get("z").toString());
//        System.out.println("目前成交價格:\t"+nowPrice);


//        Double openPrice = Double.valueOf(jsonObject1.get("o").toString());

//        System.out.println("開盤價格:\t"+openPrice);
//        System.out.println("當日最高:\t"+jsonObject1.get("h"));
//        System.out.println("當日最低:\t"+jsonObject1.get("l"));

    }
}
