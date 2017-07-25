package com.diyiliu.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description: DbUtil
 * Author: DIYILIU
 * Update: 2017-07-13 15:57
 */
public class DbUtil {

    private static DataSource dataSource = null;

    public static DataSource getDataSource(){

        if (dataSource == null){
            initDataSource();
        }

        return dataSource;
    }

    private static void initDataSource(){

        try {
            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemResourceAsStream("jdbc.properties"));

            dataSource =  DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
