package com.bigmonkey.jackson.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigmonkey.jackson.Config.CustomeCar2Serializer;
import com.bigmonkey.jackson.Config.Views.AgeAndName;
import com.bigmonkey.jackson.Config.Views.NameOnly;
import com.bigmonkey.jackson.dto.Person;
import com.bigmonkey.jackson.dto.Person2;
import com.bigmonkey.jackson.dto.objectMapper.Car;
import com.bigmonkey.jackson.dto.objectMapper.Car2;
import com.bigmonkey.jackson.dto.objectMapper.ObjectPerson;
import com.bigmonkey.jackson.dto.objectMapper.User;
import com.bigmonkey.jackson.dto.objectMapper.User2;
import com.bigmonkey.jackson.dto.objectMapper.User3;
import com.bigmonkey.jackson.dto.objectMapper.User4;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.RequiredArgsConstructor;

// JsonNode
@RequiredArgsConstructor
@RestController
public class Hello2Controller {
    private final ObjectMapper objectMapper;

    // json view 사용
    @GetMapping("/objectMapper2/test1")
    public Object objectMappertest1() throws JsonProcessingException {

        User4 user = createDummyUser4();
        String s = objectMapper.writeValueAsString(user);

        JsonNode jsonNode = objectMapper.readTree(s);
        JsonNode messages = jsonNode.get("messages");
        JsonNode jsonNode1 = messages.get(0);

        System.out.println("jsonNode1 = " + jsonNode1);
        return "see log";
    }

    // type reference 사용
    @GetMapping("/objectMapper2/test2")
    public Object objectMappertest2() throws JsonProcessingException {

      /*  Car car = new Car("black", "bmw");
        Car car1 = new Car("while", "hyudae");

        List<Car> carList = List.of(car, car1);
        String s = objectMapper.writeValueAsString(carList);*/

        String val = "[{\"color\":\"black\",\"type\":\"bmw\"},{\"color\":\"while\",\"type\":\"hyudae\"}]";
        List<Car> listCar = objectMapper.readValue(val, new TypeReference<List<Car>>() {});

        return listCar;
    }

    // custom 직렬화기, 역직렬화기 사용
    @GetMapping("/objectMapper2/test3")
    public Object objectMappertest3() throws JsonProcessingException {
        SimpleModule module = new SimpleModule("CustomeCarSerializer",
            new Version(1, 0, 0, null, null, null));
        module.addSerializer(Car2.class, new CustomeCar2Serializer());
        objectMapper.registerModule(module);

        Car2 build = Car2.builder()
            .color("black")
            .money(300)
            .type("bmw")
            .build();

        String s = objectMapper.writeValueAsString(build);
        return s;
    }


    private User4 createDummyUser4() {
        User4 user = new User4();

        user.setName("test");
        user.setAge(30);

        List<String> list = List.of("hello 1", "hello 2");
        user.setMessages(list);

        return user;
    }
}
