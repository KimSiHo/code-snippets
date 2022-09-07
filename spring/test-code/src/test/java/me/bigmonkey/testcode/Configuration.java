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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.bigmonkey.testcode.src.MyBean;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
@SpringBootTest(classes = TestCodeApplication.class)
public class Configuration {

    private final MyBean myBean;

    @Value("test")
    String hi;
    @Value("test-dev")
    String hiDev;

    @Test
    void simpleTest() {
        System.out.println("hi");
        System.out.println("hi = " + hi);
        System.out.println("hiDev = " + hiDev);

        Assert.assertNotNull(myBean);
        myBean.hello();
    }


}
