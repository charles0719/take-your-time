package com.charles.srewsample;

import cn.smallbun.screw.core.mapping.Mapping;
import cn.smallbun.screw.core.query.mysql.model.MySqlTableModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author charles
 * @date 2021/2/8 17:15
 */
public class ConnectionTest {
    @Test
    public void t1() throws Exception{
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/charles?serverTimezone=UTC");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        Connection connection = dataSource.getConnection();
        System.out.println(connection.getCatalog());
        System.out.println(connection.getSchema());
        ResultSet resultSet = connection.getMetaData().getTables(connection.getCatalog(), connection.getSchema(), "%", new String[]{"table"});
        List<MySqlTableModel> models = Mapping.convertList(resultSet, MySqlTableModel.class);
        System.out.println(models);
    }
}
