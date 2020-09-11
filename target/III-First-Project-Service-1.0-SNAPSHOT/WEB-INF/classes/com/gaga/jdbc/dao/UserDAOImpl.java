package com.gaga.jdbc.dao;

import com.gaga.jdbc.dao.InitTable.CreateAccount;
import com.gaga.jdbc.dao.delete.UserDelete;
import com.gaga.jdbc.dao.insert.UserInsert;
import com.gaga.jdbc.dao.query.UserQuery;
import com.gaga.jdbc.dao.update.UserUpdate;
import com.gaga.jdbc.pojo.UserDO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements DataBaseDAO<UserDO> {
    private UserDO user;

    public UserDAOImpl(UserDO user) {
        this.user = user;
    }

    public UserDAOImpl() {
    }

    @Override
    public List<UserDO> getLists() throws IOException, SQLException {
        return new UserQuery().listUsers();
    }

    @Override
    public UserDO getElement(String id) throws IOException, SQLException {
        return new UserQuery().query(id);
    }

    @Override
    public boolean isExist() throws IOException, SQLException {
        return isExist(user);
    }
    public boolean isExist(UserDO user) throws IOException, SQLException {
        return new UserQuery().isUserExist(user);
    }

    @Override
    public boolean setElement(UserDO ele) throws IOException, SQLException {
        return new UserUpdate(ele).update();
    }

    @Override
    public boolean addElement(UserDO ele) throws IOException, SQLException {
        return new UserInsert().insert(ele);
    }

    @Override
    public boolean remove(UserDO ele) throws IOException, SQLException {
        return new UserDelete(ele).delete();
    }

    @Override
    public boolean isEmpty() throws IOException, SQLException {
        return new CreateAccount().isExist();
    }
}
