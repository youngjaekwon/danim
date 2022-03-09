package com.danim.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.danim.member"})
public class AppConfig {
    /////////////////////////////////// DBCP /////////////////////////////////////////////////
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
}
