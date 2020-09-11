package com.gaga.jdbc.service;

import com.gaga.jdbc.dao.InitTable.CreateStockDays;
import com.gaga.jdbc.dao.StockDAOImpl;
import com.gaga.jdbc.dao.insert.StockInsert;
import com.gaga.jdbc.dao.query.StockQuery;
import com.gaga.jdbc.pojo.StockDayDO;
import com.gaga.jdbc.pojo.StockTotalNoDO;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StockService {
    private static List<StockDayDO> list;
    private static List<StockTotalNoDO> listAll;
    private static Map<String, List<StockTotalNoDO>> map;
    private StockDayDO stock;
    private String sStockNo;

    public StockService(String sStockNo) throws IOException, SQLException {
        stock = new StockDayDO();
        if(!StringUtil.isEmpty(sStockNo)){
            this.sStockNo = sStockNo;
            stock.setStockNo(new BigDecimal(sStockNo));
        }
        if(list == null) getList();
    }
    public StockService(StockDayDO stock) throws IOException, SQLException {
        this.stock = stock;
        if(list == null) getList();
    }

    public static List<StockTotalNoDO> approximateSearch(String description) throws IOException, SQLException {
        if(StringUtil.isEmpty(description)) return null;
        getAllStockNoList();
        List<StockTotalNoDO> tempList = new ArrayList<>();

        for (StockTotalNoDO ele : listAll) {
            String no = ele.getStockNo().toString();
            String name = ele.getName();
            if (no.contains(description) || description.contains(no)) {
                tempList.add(ele);
            }else if(name.contains(description) || description.contains(name)){
                tempList.add(ele);
            }
        }
        return tempList;
    }

    public static List<StockDayDO> getList() throws IOException, SQLException {
        if(list == null)list = new StockDAOImpl().getLists();
        return list;
    }
    public List<StockDayDO> updateList() throws IOException, SQLException {
        list = new StockDAOImpl().getLists();
        return list;
    }

    public static Map<String, List<StockTotalNoDO>> getAllStockNo() throws IOException, SQLException {
        if(map==null) map = new StockQuery(null).getAllStockNo();
        return map;
    }
    public static List<StockTotalNoDO> getAllStockNoList() throws IOException, SQLException {
        if(listAll == null){
            getAllStockNo();
            listAll = new ArrayList<>();
            map.values().forEach(ele->listAll.addAll(ele));
        }
        return listAll;
    }

    // yyyy-MM-dd
    // return this month data
    public List<StockDayDO> getStockByDate(String date) throws SQLException, NoSuchAlgorithmException, IOException, KeyManagementException {
        return getStockByDate(date, null);
    }
    // return interval date
    public List<StockDayDO> getStockByDate(String beginDate, String endDate) throws SQLException, NoSuchAlgorithmException, KeyManagementException, IOException {
        updateData(beginDate);

        LocalDate begin = LocalDate.parse(beginDate.subSequence(0, beginDate.length()));

        LocalDate end;
        if(endDate == null){
            end = LocalDate.now();
        }else {
            end = LocalDate.parse(endDate.subSequence(0, endDate.length()));
        }
        if(end.getMonthValue() != begin.getMonthValue()) updateData(endDate);

        getList();

        List<StockDayDO> pick = new ArrayList<>();
        for (StockDayDO stockDayDO : list) {
            if(begin.compareTo(stockDayDO.getDate().toLocalDate()) <= 0 && end.compareTo(stockDayDO.getDate().toLocalDate()) >= 0){
                pick.add(stockDayDO);
            }
        }
        return pick;
    }

    public boolean updateData() throws SQLException, NoSuchAlgorithmException, KeyManagementException, IOException {
        return updateData(null);
    }
    // yyyy-MM-dd to yyyyMMdd
    public boolean updateData(String sDate) throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        if(sDate != null) sDate = sDate.replace("-", "");

        boolean b = new StockInsert(stock).insert(sDate);
        updateList();
        return b;
    }

    public void initTable() throws IOException, NoSuchAlgorithmException, SQLException, KeyManagementException {
        new CreateStockDays().init();
        updateList();
    }

    public int delete(String sDate) throws IOException, SQLException {
        return delete(sDate, sDate);
    }
    public int delete(String begin, String end) throws IOException, SQLException {
        LocalDate beginDate = Date.valueOf(begin).toLocalDate();
//        LocalDate beginDate1 = beginDate;
        LocalDate endDate = Date.valueOf(end).toLocalDate();

        List<StockDayDO> removeList = new ArrayList<>();

        int count = 0;
        while(beginDate.compareTo(endDate) <= 0){
            if(new StockDAOImpl(stock).remove(beginDate)){
                ++count;
            }
            beginDate = beginDate.plusDays(1);
        }

        updateList();
        return count;
    }

    public StockDayDO showAvg(String sBegin) throws NoSuchAlgorithmException, SQLException, KeyManagementException, IOException {
        return showAvg(sBegin, null);
    }

    public StockDayDO showAvg(String sBegin, String sEnd) throws IOException, SQLException, KeyManagementException, NoSuchAlgorithmException {
        List<StockDayDO> interval = getStockByDate(sBegin, sEnd);

        StockDayDO avg = new StockDayDO();

        BigDecimal tradeVolume = BigDecimal.ZERO;
        BigDecimal transAction = BigDecimal.ZERO;
        BigDecimal highestPrice = BigDecimal.ZERO;
        BigDecimal lowestPrice = BigDecimal.ZERO;
        BigDecimal openingPrice = BigDecimal.ZERO;
        BigDecimal closingPrice = BigDecimal.ZERO;


        for (StockDayDO ele : interval) {
            tradeVolume = tradeVolume.add(ele.getTradeVolume());
            transAction = transAction.add(ele.getTransAction());
            highestPrice = highestPrice.add(ele.getHighestPrice());
            lowestPrice = lowestPrice.add(ele.getLowestPrice());
            openingPrice = openingPrice.add(ele.getOpeningPrice());
            closingPrice = closingPrice.add(ele.getClosingPrice());
        }
        BigDecimal length = BigDecimal.valueOf(interval.size());
        avg.setTradeVolume(tradeVolume.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setTransAction(transAction.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setHighestPrice(highestPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setLowestPrice(lowestPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setOpeningPrice(openingPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));
        avg.setClosingPrice(closingPrice.divide(length, 2, BigDecimal.ROUND_HALF_UP));

        avg.setStockNo(interval.get(0).getStockNo());
        avg.setName(interval.get(0).getName());

        return avg;
    }

    public List<List<BigDecimal>> openingPriceLine(String sBegin, String sEnd) throws SQLException, NoSuchAlgorithmException, IOException, KeyManagementException {
        List<StockDayDO> interval = getStockByDate(sBegin, sEnd);
        List<List<BigDecimal>> opLine = new ArrayList<>();

        BigDecimal openingPrice = interval.get(0).getOpeningPrice();
        opLine.add(Arrays.asList(openingPrice, BigDecimal.ZERO));
        for (int i = 1; i < interval.size(); i++) {
            BigDecimal next = interval.get(i).getOpeningPrice();
            BigDecimal diff = openingPrice.subtract(next);

            opLine.add(Arrays.asList(next, diff));
            openingPrice = next;
        }
        return opLine;
    }
}
