package me.bigmonkey.testcode;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.testcode.src.MyBean;

@Slf4j
@TestConstructor(autowireMode = AutowireMode.ALL)
@SpringBootTest(classes = TestCodeApplication.class)
public class Configuration {

    private final MyBean myBean;

    @Value("test")
    String hi;
    @Value("test-dev")
    String hiDev;

    public Configuration(MyBean myBean) {
        this.myBean = myBean;
    }

    @Test
    void simpleTest() {
        System.out.println("hi");
        System.out.println("hi = " + hi);
        System.out.println("hiDev = " + hiDev);

        Assert.assertNotNull(myBean);
        myBean.hello();
    }

    // 테스트 코드 실행 시, 스프링 컨테이너에 추가적으로 빈들을 구성
    @TestConfiguration
    static class TestConfig {

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
}
