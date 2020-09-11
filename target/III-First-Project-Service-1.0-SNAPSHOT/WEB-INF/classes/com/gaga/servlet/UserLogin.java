package com.gaga.servlet;

import com.gaga.jdbc.pojo.UserDO;
import com.gaga.jdbc.service.LoginService;
import com.gaga.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class UserLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();

        String username = String.valueOf(req.getHeader("username"));
        String password = String.valueOf(req.getHeader("password"));
        String reqType = String.valueOf(req.getHeader("reqType"));

        boolean login = false;
        try {

            if(Objects.equals(reqType, "login")) {

                login = new LoginService(username, password).login();
                String jsonData = JsonUtil.sendJsonData(login, null, null, 0);
                writer.write(jsonData);

            }else if(Objects.equals(reqType, "register")){

                boolean register = new LoginService(username, password).register();
                String jsonData = JsonUtil.sendJsonData(register, null, null, 0);
                writer.write(jsonData);

            }else if(Objects.equals(reqType, "isRegister")){

                boolean isRegister = new LoginService(username, password).isRegistered();
                String jsonData = JsonUtil.sendJsonData(isRegister, null, null, 0);
                writer.write(jsonData);

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
