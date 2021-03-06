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

        String username = String.valueOf(req.getParameter("username"));
        String password = String.valueOf(req.getParameter("password"));
        String reqType = String.valueOf(req.getParameter("reqType"));

        boolean login = false;
        String result = "null";
        try {
            LoginService service = new LoginService(username, password);
            if(Objects.equals(reqType, "login")) {
                login = service.login();
                UserDO userDO = new UserDO();
                if(login) userDO = service.getUser();

                result = JsonUtil.analyUserDO(login, userDO);

            }else if(Objects.equals(reqType, "register")){
                boolean register = service.register();
                result = JsonUtil.sendJsonData(register, null, null, 0);

            }else if(Objects.equals(reqType, "isRegister")){
                boolean isRegister = service.isRegistered();
                result = JsonUtil.sendJsonData(isRegister, null, null, 0);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        writer.write(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
