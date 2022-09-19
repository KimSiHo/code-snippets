package me.bigmonkey.structure.common.datasource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.test.member.dao.member",
    entityManagerFactoryRef = "memberEntityManager",
    transactionManagerRef = "memberTransactionManager"
)
public class MemberDataSourceConfiguration {

    @Bean(name = "memberHibernateProperties")
    @ConfigurationProperties("spring.jpa.member-properties")
    public HashMap<String, Object> hibernateProperties() {
        return new HashMap<String, Object>();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean memberEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(memberDataSource());
        em.setPackagesToScan("com.test.member.entities.member");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        final HashMap<String, Object> properties = hibernateProperties();
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager memberTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(memberEntityManager().getObject());
        return transactionManager;
    }

    @Bean(value = "memberHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource-member.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean
    public DataSource memberDataSource() {
        return new HikariDataSource(hikariConfig());
    }
}
