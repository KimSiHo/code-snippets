package me.bigmonkey.java.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//pattern 과 matcher의 기본 사용식
@Component
public class PatternMatch implements ApplicationRunner {

    //영문자만
    String regex = "^[a-zA-Z]*$";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //test1();
        //test2();
        //test3();
        test4();
    }

    /**
     * 그룹 기능
     */
    private void test4() {
        String[] as = {
            "나는 바보입니다",
            "나는 개입니다",
            "나는 새입니다",
            "나는 코끼리입니다",
            "나는 개구리입니다"
        };

        /*group0은 전체를 가져오고, group1은 매칭된 문자열을 가져온다*/
        Pattern compile = Pattern.compile("나는 (.*)입니다");
        for (String a : as) {
            Matcher matcher = compile.matcher(a);
            if(matcher.matches()) {
                System.out.println(matcher.group(0));
                System.out.println(matcher.group(1));
            } else {
                System.out.println("일치 안함!");
            }
        }

        System.out.println("===================");
        /*괄호 시작에 ?:를 넣으면 캡쳐하지 않음*/
        /*해당 무시 기호를 넣지 않으면 첫번째 그룹은 나 또는 너가 되어야 한다*/
        compile = Pattern.compile("(?:.*)는 (.*)입니다");
        for (String a : as) {
            Matcher matcher = compile.matcher(a);
            if(matcher.matches()) {
                System.out.println(matcher.group(0));
                System.out.println(matcher.group(1));
            } else {
                System.out.println("일치 안함!");
            }
        }

        System.out.println("===================");
        /*캡쳐된 그룹은 Matcher를 통해서 가져올 수도 있지만, $2 같은 문법을 통해서도 가져올 수 있다 0은 전체 문자열*/
        for (String a : as) {
            System.out.println(a.replaceAll("(.*)는 (.*)입니다", "$2는 $1입니다"));
        }

        System.out.println("===================");
        // 그룹에 이름을 지정할 수 있다
        for (String a : as) {
            System.out.println(a.replaceAll("(.*)는 (?<animal>.*)입니다", "${animal}는 $1입니다"));
        }
    }

    /**
     * 일치 개수 count
     */
    private void test3() {
        // 자바 9부터 가능
        String hello = "HelloxxxHelloxxxHello";
        Pattern pattern = Pattern.compile("Hello");
        Matcher matcher = pattern.matcher(hello);
        long count = matcher.results().count();
        System.out.println("count = " + count);

        //자바 8에서는
        matcher.reset();
        count = 0;
        while (matcher.find())
            count++;
        System.out.println("count = " + count);

        /**
         * 중첩된 문자열에서 찾을 때는 루프문을 통해서 index를 +1 씩 증가시켜 가면서
         */
        String hello2 = "aaaa";
        Pattern pattern2 = Pattern.compile("aa");
        Matcher matcher2 = pattern2.matcher(hello2);
        System.out.println("matcher2.results().count() = " + matcher2.results().count());

        count = 0;
        int i = 0;
        matcher2.reset();
        while (matcher2.find(i)) {
            count++;
            i = matcher2.start() + 1;
        }

        System.out.println("count = " + count);
    }

    /**
     * pattern, matcher
     */
    private void test2() {
        // 정규 표현식을 컴파일해서 갖고 있다
        // find를 영문자를 찾았으므로 true를 리턴하고 시작 인덱스와 종료 인덱스 0, 2를 출력
        // 그리고 matcher를 리셋하고 자바 9이상부터 지원하는 스트림을 통해서 일치 카운트를 리턴
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("aa");
        System.out.println(matcher.find());
        System.out.println(matcher.start());
        System.out.println(matcher.end());
        matcher.reset();
        long count = matcher.results().count();
        System.out.println("count = " + count);
    }

    private void test1() {
        //간단한 검사를 하려면 matches 메소드를
        Pattern.matches(regex, "");
    }
}
