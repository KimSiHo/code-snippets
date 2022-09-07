package com.bigmonkey.jackson;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootTest
public class JacksonDeserializeTest {
    // json 문자열을 객체로 읽을 때는 setter기반으로 한다!
    // setter 가있을 경우 setter 기반이지만 없어도 되고 기본 생성자만 있어도 된다..
    // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 매핑되지 않는 프로퍼티 발견 시 예외 발생 X 하는 설정

    @DisplayName("기본 readValue 메소드 테스트")
    @Test
    public void basicReadTest() throws JsonProcessingException {
        String jsonStr = "{\"id\" : 1, \"name\" : \"Anna\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(jsonStr, Student.class);

        System.out.println(student);
    }

    @DisplayName("type reference를 사용한 변환 테스트")
    @Test
    public void typeReferenceTest() throws JsonProcessingException {
        String jsonStr = "{\"id\" : 1, \"name\" : \"Anna\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonStr, new TypeReference<>() {});

        System.out.println(jsonMap);
    }

    @DisplayName("json array를 자바 배열로 변환 테스트")
    @Test
    public void jsonArrayTest() throws JsonProcessingException {
        String jsonArrStr = "[{\"id\" : 1, \"name\" : \"Anna\"}, {\"id\" : 2, \"name\" : \"Brian\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Student[] studentArr = objectMapper.readValue(jsonArrStr, Student[].class);

        System.out.println(Arrays.toString(studentArr));
    }

    @DisplayName("json array를 자바 리스트로 변환 테스트")
    @Test
    public void jsonListTest() throws JsonProcessingException {
        String jsonArrStr = "[{\"id\" : 1, \"name\" : \"Anna\"}, {\"id\" : 2, \"name\" : \"Brian\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Student> studentList = objectMapper.readValue(jsonArrStr, new TypeReference<>() {});

        System.out.println(studentList);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Student {

        private int id;
        private String name;
    }

    // @JsonCreator는 역직렬화에 사용되는 생성자 또는 팩토리 메소드를 미세 조정하는 데 사용됩니다.
    // @JsonProperty를 사용하여 동일한 결과를 얻을 수도 있습니다.
    /*@DisplayName("@JsonCreator 테스트")
    @Test
    public void jsonCreatorTest() throws JsonProcessingException {
        String json = "{\"id\":1,\"theName\":\"Mark\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Student student = objectMapper.readerFor(Student.class).readValue(json);
        System.out.println(student.rollNo +", " + student.name);
    }

    static class Student {
        public String name;
        public int rollNo;

        @JsonCreator
        public Student(@JsonProperty("theName") String name, @JsonProperty("id") int rollNo){
            this.name = name;
            this.rollNo = rollNo;
        }
    }*/


    // @JacksonInject는 Json 입력에서 파싱되지 않고 속성 값을 주입할 때 사용됩니다. 아래 예에서는 Json에서 구문 분석하는 대신 개체에 값을 삽입합니다.
    /*@DisplayName("@JacksonInject 테스트")
    @Test
    public void jacksonInjectTest() throws JsonProcessingException {
        String json = "{\"name\":\"Mark\"}";
        InjectableValues injectableValues = new InjectableValues.Std().addValue(int.class, 1);
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.reader(injectableValues).forType(Student.class).readValue(json);

        System.out.println(student.rollNo +", " + student.name);
    }

    static class Student {
        public String name;

        @JacksonInject
        public int rollNo;
    }*/


    // @JsonAnySetter allows a setter method to use Map which is then used to deserialize the additional properties of JSON in the similar fashion as other properties.
    // json 문자열의 추가적인 프로퍼티들을 다른 프로퍼티처럼 setter를 통해서 맵으로 역직렬화할 수 있다? 정도의 의미 일려나.
    /*@DisplayName("@JsonAnySetter 테스트")
    @Test
    public void jsonAnySetterTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"RollNo\" : \"1\",\"Name\" : \"Mark\"}";
        Student student = mapper.readerFor(Student.class).readValue(jsonString);

        System.out.println(student.getProperties().get("Name"));
        System.out.println(student.getProperties().get("RollNo"));
    }

    static class Student {
        private Map<String, String> properties;
        public Student(){
            properties = new HashMap<>();
        }
        public Map<String, String> getProperties(){
            return properties;
        }
        @JsonAnySetter
        public void add(String property, String value){
            properties.put(property, value);
        }
    }*/


    // @JsonSetter를 사용하면 특정 메서드를 setter 메서드로 표시할 수 있습니다.
    /*@DisplayName("@JsonSetter 테스트")
    @Test
    public void jsonAnySetterTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"rollNo\":1,\"name\":\"Marks\"}";

        Student student = mapper.readerFor(Student.class).readValue(jsonString);
        System.out.println(student.name);
    }

    static class Student {
        public int rollNo;
        public String name;

        @JsonSetter("name")
        public void setTheName(String name) {
            this.name = name;
        }
    }*/


    // @JsonDeserialize는 json 객체를 언마샬링하기 위해 커스텀 디시리얼라이저를 지정하는 데 사용됩니다.
    /*@DisplayName("@JsonDeserialize 테스트")
    @Test
    public void jsonDeserializeTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"name\":\"Mark\",\"dateOfBirth\":\"20-12-1984\"}";
        Student student = mapper.readerFor(Student.class).readValue(jsonString);

        System.out.println(student.dateOfBirth);
    }

    static class Student {
        public String name;

        @JsonDeserialize(using = CustomDateDeserializer.class)
        public Date dateOfBirth;
    }

    static class CustomDateDeserializer extends StdDeserializer<Date> {

        private static final long serialVersionUID = 1L;
        private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        public CustomDateDeserializer() {
            this(null);
        }

        public CustomDateDeserializer(Class<Date> t) {
            super(t);
        }

        @Override
        public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

            String date = parser.getText();
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/


    // @JsonEnumDefaultValue는 기본값을 사용하여 알 수 없는 열거형 값을 역직렬화하는 데 사용됩니다.
    /*@DisplayName("@JsonDeserialize 테스트")
    @Test
    public void jsonDeserializeTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        String jsonString = "\"abc\"";
        LETTERS value = mapper.readValue(jsonString, LETTERS.class);

        System.out.println(value);
    }

    enum LETTERS {
        A, B, @JsonEnumDefaultValue UNKNOWN
    }*/
}
