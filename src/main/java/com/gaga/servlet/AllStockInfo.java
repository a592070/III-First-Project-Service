package com.gaga.servlet;

import com.gaga.jdbc.pojo.StockTotalNoDO;
import com.gaga.jdbc.service.StockService;
import com.gaga.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class AllStockInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        String type = String.valueOf(req.getHeader("reqType"));
        try {
            if(Objects.equals(type, "map")) {
                List keys = new ArrayList();
                List values = new ArrayList();

                Map<String, List<StockTotalNoDO>> allStockNo = StockService.getAllStockNo();
                allStockNo.forEach((k, v) -> {
                    keys.add(k);
                    values.add(v);

                });

                String s = JsonUtil.sendJsonData(true, keys, values, JsonUtil.ANALYTIC_STACKTOTALDO_MAP);
                writer.write(s);
            }else if(Objects.equals(type, "list")){
                List<StockTotalNoDO> allStockNoList = StockService.getAllStockNoList();
                String s = JsonUtil.sendJsonData(true, allStockNoList, allStockNoList, JsonUtil.ANALYTIC_STACKTOTALDO);
                writer.write(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
