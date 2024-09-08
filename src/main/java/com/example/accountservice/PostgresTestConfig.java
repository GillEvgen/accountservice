package com.example.accountservice;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
public class PostgresTestConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/account_service");  // Адрес тестовой базы данных
        dataSource.setUsername("postgres");  // Имя пользователя тестовой базы данных
        dataSource.setPassword("1234");  // Пароль тестовой базы данных
        return dataSource;
    }
}