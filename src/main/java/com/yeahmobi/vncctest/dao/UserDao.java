package com.yeahmobi.vncctest.dao;

import com.yeahmobi.vncctest.model.User;

import java.util.List;

/**
 * Created by steven.liu on 2016/4/15.
 */
public interface UserDao {

    public void insert(User user);
    public void delete(int id);
    public void update(User user);
    public User select(int id);
    public List find();
    public User selectByName(String name,String password);
}
