package com.yeahmobi.vncctest.util;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by steven.liu on 2016/4/15.
 */
public class Initialization implements InitializingBean {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void afterPropertiesSet() throws Exception{

        jdbcTemplate.execute("drop all opjects;");
        jdbcTemplate.execute("runscript from '" + new DefaultResourceLoader().getResource("schema.sql").getURL().toString() + "'");
    }
}
