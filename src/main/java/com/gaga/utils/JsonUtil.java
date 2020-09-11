package com.gaga.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.gaga.jdbc.pojo.StockDayDO;
import com.gaga.jdbc.pojo.StockTotalNoDO;
import com.gaga.jdbc.pojo.UserDO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static final int ANALYTIC_USERDO = 1;
    public static final int ANALYTIC_STACKDO = 2;
    public static final int ANALYTIC_STACKTOTALDO = 3;
    public static final int ANALYTIC_STACKTOTALDO_MAP = 4;


    public static String sendJsonData(boolean isSuccess, List field, List data, int type){
        JSONObject obj = new JSONObject();
        obj.put("isSuccess", isSuccess);

        if(field == null || data == null){
            obj.put("field", "null");
            obj.put("data", "null");
        }else {
            List fieldList = analyListField(field , type);
            List dataList = analyListData(data, type);

            obj.put("field", fieldList);
            obj.put("data", dataList);
        }

        return obj.toJSONString();
    }

    public static List<String> analyListField(List list , int type){
        switch (type){
            case JsonUtil.ANALYTIC_STACKTOTALDO_MAP:
                return list;
            default:
                return analyListField1(list);
        }
    }

    private static <E> List<String> analyListField1(List<E> list){
        List<String> fieldList = new ArrayList<>();
        Class aClass = list.get(0).getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldList.add(field.getName());
        }
        return fieldList;
    }


    public static List<String> analyListData(List list , int type){
        List<String> dataList = new ArrayList<>();
        switch (type){
            case ANALYTIC_USERDO:
                dataList = analyListData1(list);
                break;
            case ANALYTIC_STACKDO:
                dataList = analyListData2(list);
                break;
            case ANALYTIC_STACKTOTALDO:
                dataList = analyListData3(list);
                break;
            case ANALYTIC_STACKTOTALDO_MAP:
                dataList = analyListData4(list);
                break;
        }
        return dataList;
    }
    private static List<JSONArray> analyListData1(List<UserDO> list){
        List<JSONArray> dataList = new ArrayList<>();
        for (UserDO ele : list) {
            dataList.add(ele.toJsonArray());
        }
        return dataList;
    }
    private static List<JSONArray> analyListData2(List<StockDayDO> list){
        List<JSONArray> dataList = new ArrayList<>();
        for (StockDayDO ele : list) {
            dataList.add(ele.toJsonArray());
        }
        return dataList;
    }
    private static List<JSONArray> analyListData3(List<StockTotalNoDO> list){
        List<JSONArray> dataList = new ArrayList<>();
        for (StockTotalNoDO ele : list) {
            dataList.add(ele.toJsonArray());
        }
        return dataList;
    }
    private static List<JSONArray> analyListData4(List<List<StockTotalNoDO>> list){
        List<JSONArray> dataList = new ArrayList<>();
        for (List<StockTotalNoDO> eleLis : list) {
            JSONArray arr = new JSONArray();
            for (StockTotalNoDO ele : eleLis) {
                arr.add(ele.toJsonArray());
            }
            dataList.add(arr);
        }
        return dataList;
    }
}
