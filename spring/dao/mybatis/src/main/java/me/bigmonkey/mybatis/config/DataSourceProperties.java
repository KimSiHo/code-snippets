package me.bigmonkey.mybatis.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories
public class DataSourceProperties {

    @Primary
    @Bean(name = "memberDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-member.hikari")
    public DataSource memberDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "commonDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-common.hikari")
    public DataSource commonDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "logDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-log.hikari")
    public DataSource logDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
