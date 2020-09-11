package com.gaga.jdbc.service;

public class StringUtil {
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())) return true;
        return false;
    }
}
