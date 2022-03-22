package com.danim.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Configuration
@ComponentScan(basePackages = {"com.danim"})
public class AppConfig {
    // DBCP
    @Bean(name = "dataSourceCP")
    public DataSource dataSourceCP() {
        com.zaxxer.hikari.HikariConfig config = new HikariConfig();
        config.setPoolName("springHikariCP");
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);
        config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
        config.setUsername("scott");
        config.setPassword("tiger");
        config.addDataSourceProperty("characterEncoding","UTF-8");
        config.addDataSourceProperty("useUnicode","true");

        com.zaxxer.hikari.HikariDataSource dataSourceCP = new HikariDataSource(config);

        return dataSourceCP;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSourceCP());
    }

    @Bean(name = "jsonPaeser")
    public JSONParser jsonParser(){
        return new JSONParser();
    }

    @Bean(name = "formatter")
    public DecimalFormat formatter(){
        return new DecimalFormat("#,###,###");
    }

    @Bean(name = "simpleDateFormat")
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @Bean(name = "simpleDateFormatIncludeTime")
    public SimpleDateFormat simpleDateFormatIncludeTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

}
