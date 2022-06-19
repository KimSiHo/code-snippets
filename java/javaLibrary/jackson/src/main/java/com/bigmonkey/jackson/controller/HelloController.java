package com.bigmonkey.jackson.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigmonkey.jackson.Config.Views;
import com.bigmonkey.jackson.Config.Views.AgeAndName;
import com.bigmonkey.jackson.Config.Views.NameOnly;
import com.bigmonkey.jackson.dto.Person;
import com.bigmonkey.jackson.dto.Person2;
import com.bigmonkey.jackson.dto.objectMapper.ObjectPerson;
import com.bigmonkey.jackson.dto.objectMapper.User;
import com.bigmonkey.jackson.dto.objectMapper.User2;
import com.bigmonkey.jackson.dto.objectMapper.User3;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

import lombok.RequiredArgsConstructor;

//feautre 와 기본적인 출력 매핑 방법과 읽어 들이는 방법
@RequiredArgsConstructor
@RestController
public class HelloController {
    private final ObjectMapper objectMapper;

    // getter 프로퍼티를 기준으로 출력 이름이 결정된다
    // 멤버 변수로 출력 이름을 정하려면 @JsonProperty를 붙인다
    @GetMapping("/jsonTest1")
    public Object test1() {
        Person person = new Person(2, "name", "developer");
        return person;
    }

    // jsonAutoDetect를 통해서 매핑 정책을 바꾸면 프로퍼티와 getter를 동시에 하게 할 수 있다
    // @JsonIgnore를 붙여서 특정 getter를 제외할 수 있다
    // 즉 jsonAutoDetect를 각 접근 방법의(멤버 또는 게터) 레벨을 조정할 수 있다
    @GetMapping("/jsonTest2")
    public Object test2() {
        Person2 person2 = new Person2(2, "name", "developer");
        return person2;
    }

    @GetMapping("/objectMapper/test1")
    public Object objectMappertest1() throws JsonProcessingException {

        ObjectPerson person = new ObjectPerson(2, "name", "developer");
        String value = objectMapper.writeValueAsString(person);



        return value;
    }

    // json 문자열을 객체로 읽을 때는 setter기반으로 한다!
    // setter 가있을 경우 setter 기반이지만 없어도 되고 기본 생성자만 있어도 된다..
    @GetMapping("/objectMapper/test2")
    public Object objectMappertest2() throws JsonProcessingException {

        ObjectPerson person = new ObjectPerson(2, "name", "developer");
        String value = objectMapper.writeValueAsString(person);
        System.out.println("value = " + value);

        ObjectPerson objectPerson = objectMapper.readValue(value, ObjectPerson.class);
        return objectPerson;
    }

    // 복잡한 json 객체 쓰기
    @GetMapping("/objectMapper/test3")
    public Object objectMappertest3() throws JsonProcessingException {

        User user = createDummyUser();
        String s = objectMapper.writeValueAsString(user);
        System.out.println("s = " + s);

        String s1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        System.out.println("s1 = " + s1);
        return "see log";
    }

    // json view 사용
    @GetMapping("/objectMapper/test4")
    public Object objectMappertest4() throws JsonProcessingException {

        User2 user = createDummyUser2();
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

        String s = objectMapper.writerWithView(NameOnly.class).writeValueAsString(user);
        System.out.println("s = " + s);

        String s1 = objectMapper.writerWithView(AgeAndName.class).writeValueAsString(user);
        System.out.println("s1 = " + s1);

        return "see log";
    }

    // FAIL_ON_UNKNOWN_PROPERTIES
    // json 문자열에 없는 프로퍼티 있을 시 예외 발생 무시 사용
    // 기본이 false인 것 같다 true 하면 예외 나긴 하는데 false 설정 주석 처리해도 정상 작동
    @GetMapping("/objectMapper/test5")
    public Object objectMappertest5() throws JsonProcessingException {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User3 user = createDummyUser3();
        String s1 = objectMapper.writeValueAsString(user);
        System.out.println("s1 = " + s1);

        /*String s = "{\"name\":\"test\",\"age\":30,\"messages\":[\"hello 1\",\"hello 2\"]}";
        User3 user3 = objectMapper.readValue(s, User3.class);*/

        String s2 = "{\"name\":\"test\",\"age\":30,\"messages\":[\"hello 1\",\"hello 2\"], \"name2\":\"test\"}";
        User3 user33 = objectMapper.readValue(s2, User3.class);

        return user33;
    }

    private User createDummyUser() {
        User user = new User();

        user.setName("test");
        user.setAge(30);

        List<String> list = List.of("hello 1", "hello 2");
        user.setMessages(list);

        return user;
    }

    private User2 createDummyUser2() {
        User2 user = new User2();

        user.setName("test");
        user.setAge(30);

        List<String> list = List.of("hello 1", "hello 2");
        user.setMessages(list);

        return user;
    }

    private User3 createDummyUser3() {
        User3 user = new User3();

        user.setName("test");
        user.setAge(30);

        List<String> list = List.of("hello 1", "hello 2");
        user.setMessages(list);

        return user;
    }
}
