package me.bigmonkey.mvc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;

@Controller
public class ThymeleafController {

    // 기본 텍스트 출력
    @GetMapping("/thymeleaf/test1")
    public String thymeleafTest1(Model model) {
        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "text/test1";
    }

    // unescape 텍스트 출력
    @GetMapping("/thymeleaf/test2")
    public String thymeleafTest2(Model model) {
        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "text/test2";
    }

    // 데이터 매핑
    @GetMapping("/thymeleaf/test3")
    public String thymeleafTest3(Model model) {
        User user = new User("sihoKim", 30);
        model.addAttribute("user", user);

        List<User> list = new ArrayList<>();
        list.add(user);
        model.addAttribute("userList", list);

        Map<String, User> map = new HashMap<>();
        map.put("userA", user);
        model.addAttribute("userMap", map);

        return "text/test3";
    }

    // 기본 객체들
    @GetMapping("/thymeleaf/test4")
    public String thymeleafTest4(HttpSession httpSession) {
        httpSession.setAttribute("sessionData", "Hello Session");

        return "text/test4";
    }

    // 유틸리티 객체와 날짜
    @GetMapping("/thymeleaf/test5")
    public String thymeleafTest5(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "text/test5";
    }

    // URL 링크
    @GetMapping("/thymeleaf/test6")
    public String thymeleafTest6(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");

        return "text/test6";
    }

    // 리터럴
    @GetMapping("/thymeleaf/test7")
    public String thymeleafTest7(Model model) {
        model.addAttribute("data", "spring");

        return "text/test7";
    }

    // 연산자
    @GetMapping("/thymeleaf/test8")
    public String thymeleafTest8(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "spring");

        return "text/test8";
    }

    // 속성값 설정
    @GetMapping("/thymeleaf/test9")
    public String thymeleafTest9(Model model) {

        return "text/test9";
    }

    // 반복 map을 포함한 iteralbe과 enumration을 구현한 클래스의 인스턴스를 담을 수 있다
    @GetMapping("/thymeleaf/test10")
    public String thymeleafTest10(Model model) {
        addUsers(model);

        return "text/test10";
    }

    //조건부 평가
    @GetMapping("/thymeleaf/test11")
    public String thymeleafTest11(Model model) {
        addUsers(model);
        return "text/test11";
    }

    //주석
    @GetMapping("/thymeleaf/test12")
    public String thymeleafTest12(Model model) {
        model.addAttribute("data", "spring");
        return "text/test12";
    }

    //블록, th:block은 html 태그가 아닌 타임리프의 유일한 자체 태그
    @GetMapping("/thymeleaf/test13")
    public String thymeleafTest13(Model model) {
        addUsers(model);
        return "text/test13";
    }

    //자바스크립트 인라인
    @GetMapping("/thymeleaf/test14")
    public String thymeleafTest14(Model model) {
        model.addAttribute("user", new User("UserA", 10));
        addUsers(model);

        return "text/test14";
    }

    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("UserA", 10));
        list.add(new User("UserB", 20));
        list.add(new User("UserC", 30));

        model.addAttribute("users", list);
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "hello " + data;
        }
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
