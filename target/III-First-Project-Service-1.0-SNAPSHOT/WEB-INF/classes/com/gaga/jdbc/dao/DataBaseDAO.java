package com.gaga.jdbc.dao;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface DataBaseDAO<E> {
    // getLists
    List<E> getLists() throws IOException, SQLException;
    // getEle
    E getElement(String id) throws IOException, SQLException;

    // isExist(E)
    boolean isExist() throws IOException, SQLException;

    // setEle
    boolean setElement(E ele) throws IOException, SQLException;
    // addEle
    boolean addElement(E ele) throws IOException, SQLException, NoSuchAlgorithmException, KeyManagementException;
    // remove
    boolean remove(E ele) throws IOException, SQLException;


    // isEmpty
    boolean isEmpty() throws IOException, SQLException;

}
