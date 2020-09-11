package com.gaga.servlet;

import com.gaga.jdbc.pojo.StockDayDO;
import com.gaga.jdbc.service.StockService;
import com.gaga.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class AllStockData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();


        String type = String.valueOf(req.getHeader("reqType"));

        try {

            List<StockDayDO> list = StockService.getList();
            String s = JsonUtil.sendJsonData(true, list, list, JsonUtil.ANALYTIC_STACKDO);

            writer.write(s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
