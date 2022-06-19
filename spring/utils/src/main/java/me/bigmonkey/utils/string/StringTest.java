package me.bigmonkey.utils.string;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StringTest implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String test1 = "";
        String test2 = null;
        String test3 = " ";

        // 공백 문자열이면 true  결과값 > false, false, true
        System.out.println(StringUtils.hasLength(test1));
        System.out.println(StringUtils.hasLength(test2));
        System.out.println(StringUtils.hasLength(test3));

        // false, false, false
        System.out.println(StringUtils.hasText(test1));
        System.out.println(StringUtils.hasText(test2));
        System.out.println(StringUtils.hasText(test3));

        String test4 = "";
        String test5 = null;
        String test6 = " ";
        String test7 = "a ";
        System.out.println("==========containsWhitespace==============");
        System.out.println(StringUtils.containsWhitespace(test4));
        System.out.println(StringUtils.containsWhitespace(test5));
        System.out.println(StringUtils.containsWhitespace(test6));
        System.out.println(StringUtils.containsWhitespace(test7));

        String test8 = " a ";
        String test9 = "a b c ";
        System.out.println("==========trimAllWhitespace==============");
        System.out.println(StringUtils.trimAllWhitespace(test4));
        System.out.println(StringUtils.trimAllWhitespace(test5));
        System.out.println(StringUtils.trimAllWhitespace(test6));
        System.out.println(StringUtils.trimAllWhitespace(test7));
        String s = StringUtils.trimAllWhitespace(test8);
        System.out.println(s + ", " + s.length());
        System.out.println(StringUtils.trimAllWhitespace(test9));


    }
}
