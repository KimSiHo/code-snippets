package me.bigmonkey.usecase.시간측정;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class TestRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StopWatch stopWatch = new StopWatch("test");

        stopWatch.start("Long");
        Long longType = 0L;
        for(int i = 0; i < 1_000_000; i++) {
            longType += 1L;
        }
        stopWatch.stop();

        BigDecimal bigDecimal = BigDecimal.valueOf(0, 0);
        stopWatch.start("BigDecimal type");
        for(int i = 0; i < 1_000_000; i++) {
            bigDecimal = bigDecimal.add(BigDecimal.ONE);
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }
}
