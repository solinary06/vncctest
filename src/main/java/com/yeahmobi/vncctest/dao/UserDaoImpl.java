package com.yeahmobi.vncctest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.yeahmobi.vncctest.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * Created by steven.liu on 2016/4/15.
 */
public class UserDaoImpl implements UserDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List find(){
        String sql="select * from user";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public void delete(int id) {
        String sql="delete from user where id=?";
        jdbcTemplate.update(sql,id);
    }

    public void insert(User user) {
        String sql=" insert into user (name,password) values(?,?)";
        jdbcTemplate.update(sql,new Object[]{
                user.getName(),user.getPassword()
        });
    }

    public User select(int id) {
        String sql="select * from user where id=?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class),id);
    }

    public User selectByName(String name,String password){
        String sql="select * from user where name=? and password=?";
        try{
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class),name,password);
        }catch(Exception e){
            return null;
        }
    }

    public void update(User user) {
        String sql="update user set name=?,pwd=? where id=?";
        jdbcTemplate.update(sql,user.getName(),user.getPassword(),user.getId());
    }

    private static final class UserMapper implements RowMapper{
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
