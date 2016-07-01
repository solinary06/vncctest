package com.yeahmobi.vncctest.util;

import com.google.common.io.Files;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Statement;
import java.util.UUID;

/**
 * Created by steven.liu on 2016/4/15.
 */
public class Initialization implements InitializingBean {
    @Autowired
    BasicDataSource dataSource;

    public void afterPropertiesSet() throws Exception{
        Statement st = dataSource.getConnection().createStatement();
        InputStream inputStream = Initialization.class.getClassLoader().getResourceAsStream("schema.sql");
        File file = File.createTempFile(UUID.randomUUID().toString(), ".sql");
        byte[] buffer = new byte[1024];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        int len = 0;
        while ((len = inputStream.read(buffer)) > 0) {
            bufferedOutputStream.write(buffer, 0, len);
        }

        st.execute("drop all objects;");
        st.execute("runscript from '" + file.getAbsolutePath() + "'");
        st.close();

        inputStream.close();
        bufferedOutputStream.close();
        file.delete();

    }
}
