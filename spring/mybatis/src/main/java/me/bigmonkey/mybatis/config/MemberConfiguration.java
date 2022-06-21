package me.bigmonkey.mybatis.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value = "com.etoos.member.admin.dao.mybatis.member",
    sqlSessionFactoryRef = "memberDataFactory")
public class MemberConfiguration {

    @Autowired
    @Qualifier("memberDataSource")
    DataSource memberDataSource;

    @Primary
    @Bean(name = "memberDataFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(memberDataSource);
        factoryBean.setConfigLocation(
            new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/member-config.xml"));
        return factoryBean;
    }

    @Primary
    @Bean(name = "memberDataSessionTemplate")
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory().getObject());
    }
}
