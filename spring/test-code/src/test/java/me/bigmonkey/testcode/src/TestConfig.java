package me.bigmonkey.testcode.src;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

// 테스트 코드 실행 시, 스프링 컨테이너에 추가적으로 빈들을 구성
@TestConfiguration
public class TestConfig {

    @Bean
    DataSource dataSource() {
        /*return new DriverManagerDataSource(URL, USERNAME, PASSWORD);*/
        return null;
    }

    @Bean
    MyBean myBean() {
        return new MyBean();
    }
}