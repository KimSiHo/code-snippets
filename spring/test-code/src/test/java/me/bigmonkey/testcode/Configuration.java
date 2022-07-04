package me.bigmonkey.testcode;

import javax.sql.DataSource;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Configuration {

    // 테스트 코드 실행 시, 스프링 컨테이너에 추가적으로 빈들을 구성
    @TestConfiguration
    static class TestConfig {

        @Bean
        DataSource dataSource() {
            /*return new DriverManagerDataSource(URL, USERNAME, PASSWORD);*/
            return null;
        }
    }


}
