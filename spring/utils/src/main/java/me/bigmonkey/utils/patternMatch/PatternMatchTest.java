package me.bigmonkey.utils.patternMatch;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

@Component
public class PatternMatchTest implements ApplicationRunner {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println(PatternMatchUtils.simpleMatch("aa", "aa"));

        String testURI = "/css/test";
        boolean match = PatternMatchUtils.simpleMatch(whitelist, testURI);
        System.out.println("match = " + match);
    }
}
