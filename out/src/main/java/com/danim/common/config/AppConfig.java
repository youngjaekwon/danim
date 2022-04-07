package com.danim.common.config;

import com.danim.member.beans.Member;
import com.danim.member.dao.MemberDao;
import com.danim.security.provider.CustomAuthenticationProvider;
import com.danim.security.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Configuration
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

    @Bean(name = "jsonParser")
    public JSONParser jsonParser(){
        return new JSONParser();
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper(){return new ObjectMapper();}

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

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024;
        final int MAX_MEMORY_SIZE = 10240;

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);   // 기본값 -1 (제한 없음), 1024 * 1024 * 10 = 10MB
//        multipartResolver.setMaxInMemorySize(MAX_MEMORY_SIZE); // 기본값 10240B, 디스크에 임시 파일을 생성하기 전에 메모리 보관 최대 바이트 크기
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }
}
