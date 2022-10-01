package me.bigmoneky.mybatis.config.mybatis;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Configuration
//@MapperScan(value = "me.bigmonkey.mybatis.dao.mybatis",
//    sqlSessionFactoryRef = "SqlSessionFactory")
public class MybatisConfiguration {

    private final DataSource dataSource;

    /*@Primary
    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(
            new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/member-config.txt"));

        *//*factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
            .getResources("classpath:mybatis/mappers/*.xml"));*//*

        Properties properties = new Properties();
        properties.put("mapUnderscoreToCamelCase", true);
        factoryBean.setConfigurationProperties(properties);

        return factoryBean;
    }*/

    /*@Primary
    @Bean(name = "SqlSessionTemplate")
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory().getObject());
    }*/
}
