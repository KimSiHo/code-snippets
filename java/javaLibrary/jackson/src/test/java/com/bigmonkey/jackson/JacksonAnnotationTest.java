package com.bigmonkey.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonAnnotationTest {

    // @JsonIgnoreProperties는 속성 또는 속성 목록을 무시하도록 표시하기 위해 클래스 수준에서 사용됩니다.
    /*@DisplayName("@JsonIgnoreProperties 테스트")
    @Test
    public void jsonIgnorePropertiesTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student(1,11,"1ab","Mark");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    @JsonIgnoreProperties({ "id", "systemId" })
    static class Student {
        public int id;
        public String systemId;
        public int rollNo;
        public String name;

        Student(int id, int rollNo, String systemId, String name){
            this.id = id;
            this.systemId = systemId;
            this.rollNo = rollNo;
            this.name = name;
        }
    }*/


    // @JsonIgnore는 필드 수준에서 속성 또는 속성 목록을 무시하도록 표시하는 데 사용됩니다.
    /*@Test
    @DisplayName("@JsonIgnoreProperties 테스트")
    public void jsonIgnorePropertiesTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student(1,11,"1ab","Mark");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        public int id;
        @JsonIgnore
        public String systemId;
        public int rollNo;
        public String name;

        Student(int id, int rollNo, String systemId, String name){
            this.id = id;
            this.systemId = systemId;
            this.rollNo = rollNo;
            this.name = name;
        }
    }*/


    // @JsonIgnoreType is used at mark a property of special type to be ignored.
    /*@Test
    @DisplayName("@JsonIgnoreType 테스트")
    public void jsonIgnoreTypeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student(1,11,"1ab","Mark");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        public int id;
        @JsonIgnore
        public String systemId;
        public int rollNo;
        public Name nameObj;

        Student(int id, int rollNo, String systemId, String name){
            this.id = id;
            this.systemId = systemId;
            this.rollNo = rollNo;
            nameObj = new Name(name);
        }

        @JsonIgnoreType
        class Name {
            public String name;
            Name(String name){
                this.name = name;
            }
        }
    }*/


    // @JsonInclude is used at exclude properties having null/empty or default values.
    /*@Test
    @DisplayName("@JsonInclude 테스트")
    public void jsonIncludeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student(1,null);
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class Student {
        public int id;
        public String name;

        Student(int id,String name){
            this.id = id;
            this.name = name;
        }
    }*/


    // jsonAutoDetect를 각 접근 방법의(멤버 또는 게터) 레벨을 조정할 수 있다
    // @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NON_PRIVATE)
   /* @Test
    @DisplayName("@JsonAutoDetect 테스트")
    public void jsonIncludeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student(1,"Mark");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    @JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
    static class Student {
        private int id;
        public String name;

        Student(int id,String name) {
            this.id = id;
            this.name = name;
        }
    }*/


    // @JsonProperty is used to mark non-standard getter/setter method to be used with respect to json property.
    // getter 프로퍼티를 기준으로 출력 이름이 결정된다, 멤버 변수로 출력 이름을 정하려면 @JsonProperty를 붙인다
    /*@Test
    @DisplayName("@JsonProperty 테스트")
    public void jsonIncludeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"id\" : 1}";
        Student student = objectMapper.readerFor(Student.class).readValue(json);

        System.out.println(student.getTheId());
    }

    static class Student {
        private int id;
        Student(){}
        Student(int id){
            this.id = id;
        }
        @JsonProperty("id")
        public int getTheId() {
            return id;
        }
        @JsonProperty("id")
        public void setTheId(int id) {
            this.id = id;
        }
    }*/


    // @JsonFormat is used to specify format while serialization or de-serialization. It is mostly used with Date fields.
    /*@Test
    @DisplayName("@JsonFormat 테스트")
    public void jsonFormatTest() throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = simpleDateFormat.parse("20-12-1984");

        Student student = new Student(1, date);
        String jsonString = objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(student);
        System.out.println(jsonString);
    }

    static class Student {
        public int id;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        public Date birthDate;
        Student(int id, Date birthDate){
            this.id = id;
            this.birthDate = birthDate;
        }
    }*/


    //@JsonUnwrapped is used to unwrap values of objects during serialization or de-serialization.
    /*@Test
    @DisplayName("@JsonUnwrapped 테스트")
    public void jsonIncludeTest() throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = simpleDateFormat.parse("20-12-1984");

        Student.Name name = new Student.Name();
        name.first = "Jane";
        name.last = "Doe";
        Student student = new Student(1, name);
        String jsonString = objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(student);
        System.out.println(jsonString);
    }

    static class Student {
        public int id;
        @JsonUnwrapped
        public Name name;
        Student(int id, Name name){
            this.id = id;
            this.name = name;
        }
        static class Name {
            public String first;
            public String last;
        }
    }*/


    //@JsonView is used to control values to be serialized or not.
    /*@Test
    @DisplayName("@JsonView 테스트")
    public void jsonViewTest() throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();

        Student student = new Student(1, "Mark", 12);
        String jsonString = objectMapper
            .writerWithDefaultPrettyPrinter()
            .withView(Views.Public.class)
            .writeValueAsString(student);
        System.out.println(jsonString);
    }

    static class Student {
        @JsonView(Views.Public.class)
        public int id;
        @JsonView(Views.Public.class)
        public String name;
        @JsonView(Views.Internal.class)
        public int age;

        Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
    static class Views {
        static class Public {}
        static class Internal extends Public {}
    }*/


    //@JsonFilter is used to apply filter during serialization/de-serialization like which properties are to be used or not.
    /*@Test
    @DisplayName("@JsonFilter 테스트")
    public void jsonFilterTest() throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();

        Student student = new Student(1,13, "Mark");

        FilterProvider filters = new SimpleFilterProvider() .addFilter(
            "nameFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));

        String jsonString = objectMapper.writer(filters)
            .withDefaultPrettyPrinter()
            .writeValueAsString(student);
        System.out.println(jsonString);
    }

    @JsonFilter("nameFilter")
    static class Student {
        public int id;
        public int rollNo;
        public String name;

        Student(int id, int rollNo, String name) {
            this.id = id;
            this.rollNo = rollNo;
            this.name = name;
        }
    }*/
}
