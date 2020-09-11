package com.gaga.servlet;

import com.gaga.jdbc.service.StockService;
import com.gaga.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class InsertData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        String stockno = String.valueOf(req.getHeader("stockno"));
        String sDate = String.valueOf(req.getHeader("date"));

        boolean isInsert;
        String result = "null";
        try {
            isInsert = new StockService(stockno).updateData(sDate);
            result = JsonUtil.sendJsonData(isInsert, null, null,0);

        } catch (NoSuchAlgorithmException | SQLException | KeyManagementException e) {
            e.printStackTrace();
        }
        writer.write(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
