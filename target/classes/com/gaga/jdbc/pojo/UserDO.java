package com.gaga.jdbc.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDO {
    private String userName;
    private String password;
    private BigDecimal favorite1;
    private BigDecimal favorite2;
    private BigDecimal favorite3;
    private BigDecimal favorite4;
    private BigDecimal favorite5;
    private Date register;
    private Date lastDate;
    private boolean isAdmin;

    public UserDO() {
        this("guest", "guest");
    }

    public UserDO(String userName, String password){
        this(userName, password, null, null, null, null, null, null, null);
    }

    public UserDO(String userName, String password, BigDecimal favorite1, BigDecimal favorite2, BigDecimal favorite3, BigDecimal favorite4, BigDecimal favorite5, Date register, Date lastDate) {
        this.userName = userName;
        this.password = password;
        this.favorite1 = favorite1;
        this.favorite2 = favorite2;
        this.favorite3 = favorite3;
        this.favorite4 = favorite4;
        this.favorite5 = favorite5;
        this.register = register;
        this.lastDate = lastDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getFavorite1() {
        return favorite1;
    }

    public void setFavorite1(BigDecimal favorite1) {
        this.favorite1 = favorite1;
    }

    public BigDecimal getFavorite2() {
        return favorite2;
    }

    public void setFavorite2(BigDecimal favorite2) {
        this.favorite2 = favorite2;
    }

    public BigDecimal getFavorite3() {
        return favorite3;
    }

    public void setFavorite3(BigDecimal favorite3) {
        this.favorite3 = favorite3;
    }

    public BigDecimal getFavorite4() {
        return favorite4;
    }

    public void setFavorite4(BigDecimal favorite4) {
        this.favorite4 = favorite4;
    }

    public BigDecimal getFavorite5() {
        return favorite5;
    }

    public void setFavorite5(BigDecimal favorite5) {
        this.favorite5 = favorite5;
    }

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register) {
        this.register = register;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDO userDO = (UserDO) o;
        return userName.equals(userDO.userName) &&
                Objects.equals(password, userDO.password) &&
                Objects.equals(register, userDO.register);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, register);
    }

    @Override
    public String toString() {
        return "[" +
                "userName='" + userName +
                ", password='" + password +
                ", favorite1=" + favorite1 +
                ", favorite2=" + favorite2 +
                ", favorite3=" + favorite3 +
                ", favorite4=" + favorite4 +
                ", favorite5=" + favorite5 +
                ", register=" + register +
                ", lastDate=" + lastDate +
                ", isAdmin=" + isAdmin +
                ']';
    }
    public JSONArray toJsonArray(){
        String data = "[" + "\"" + userName + "\"" + ","+
                "\"" + password + "\"" + "," +
                "\"" + String.valueOf(favorite1) + "\"" +","+
                "\"" + String.valueOf(favorite2) + "\"" +","+
                "\"" + String.valueOf(favorite3) + "\"" +","+
                "\"" + String.valueOf(favorite4) + "\"" +","+
                "\"" + String.valueOf(favorite5) + "\"" +","+
                "\"" + String.valueOf(register) + "\"" +","+
                "\"" + String.valueOf(lastDate) + "\"" +","+
                "\"" + String.valueOf(isAdmin) + "\"" +"]";

        return JSONArray.parseArray(data);
    }
}
