package com.bigmonkey.jackson;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootTest
public class JacksonSerializeTest {
    // Map 타입의 멤버변수의 getter 위에 선언한다. json 변환 시 "key":"value" 형식으로 나온다. depth를 줄여준다
    /*@DisplayName("@JsonAnyGetter 테스트")
    @Test
    public void jsonAnyGetterTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student();
        student.add("Name", "Mark");
        student.add("RollNo", "1");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        private Map<String, String> properties;
        public Student(){
            properties = new HashMap<>();
        }
        @JsonAnyGetter
        public Map<String, String> getProperties(){
            return properties;
        }
        public void add(String property, String value){
            properties.put(property, value);
        }
    }
*/

    // @JsonGetter를 사용하면 특정 메서드를 getter 메서드로 표시할 수 있습니다.
    /*@DisplayName("@JsonGetter 테스트")
    @Test
    public void jsonGetterTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student("Mark", 1);
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        private String name;
        private int rollNo;

        public Student(String name, int rollNo){
            this.name = name;
            this.rollNo = rollNo;
        }

        @JsonGetter // 이렇게 하면 name과 studentName이 중복되고 studentName위에 name 값을 주면서 어노테이션을 쓰면 중복되지 않는다
        // JsonProperty로도 동일한 기능을 할 수 있다
        public String getName(){
            return name;
        }

        public String getStudentName(){
            return name;
        }

        public int getRollNo() {
            return rollNo;
        }
    }*/


    /*@DisplayName("@JsonPropertyOrder 테스트")
    @Test
    public void jsonPropertyOrderTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student("Mark", 1);
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    @JsonPropertyOrder({ "rollNo", "name" })
    static class Student {
        private String name;
        private int rollNo;

        public Student(String name, int rollNo) {
            this.name = name;
            this.rollNo = rollNo;
        }

        public String getName(){
            return name;
        }

        public int getRollNo(){
            return rollNo;
        }
    }*/


    // @JsonRawValue를 사용하면 이스케이프 또는 장식 없이 텍스트를 직렬화할 수 있습니다.
    /*@DisplayName("@JsonRawValue 테스트")
    @Test
    public void jsonRawValueTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student("Mark", 1, "{\"attr\":false}");
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        private String name;
        private int rollNo;
        @JsonRawValue
        private String json;
        public Student(String name, int rollNo, String json){
            this.name = name;
            this.rollNo = rollNo;
            this.json = json;
        }
        public String getName(){
            return name;
        }
        public int getRollNo(){
            return rollNo;
        }
        public String getJson(){
            return json;
        }
    }*/


    // @JsonValue를 사용하면 단일 메서드를 사용하여 전체 개체를 직렬화할 수 있습니다. (@JsonValue allows to serialize an entire object using its single method.)
    /*@DisplayName("@JsonValue 테스트")
    @Test
    public void jsonValueTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student("Mark", 1);
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        private String name;
        private int rollNo;
        public Student(String name, int rollNo){
            this.name = name;
            this.rollNo = rollNo;
        }
        public String getName(){
            return name;
        }
        public int getRollNo(){
            return rollNo;
        }
        @JsonValue
        public String toString(){
            return "{ name : " + name + " }";
        }
    }*/


    // @JsonRootName을 사용하면 JSON을 통해 루트 노드를 지정할 수 있습니다. We need to enable wrap root value as well.
    /*@DisplayName("@JsonRootName 테스트")
    @Test
    public void jsonRootNameTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = new Student("Mark", 1);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    @JsonRootName(value = "student")
    class Student {
        private String name;
        private int rollNo;

        public Student(String name, int rollNo){
            this.name = name;
            this.rollNo = rollNo;
        }
        public String getName(){
            return name;
        }
        public int getRollNo(){
            return rollNo;
        }
    }*/


    // @JsonSerialize는 json 개체를 마샬링하기 위해 사용자 지정 직렬 변환기를 지정하는 데 사용됩니다.
    // (@JsonSerialize is used to specify custom serializer to marshall the json object.)
    /*@DisplayName("@JsonSerialize 테스트")
    @Test
    public void jsonSerializeTest() throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Student student = new Student("Mark", 1, dateFormat.parse("20-11-1984"));
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);

        System.out.println(jsonString);
    }

    static class Student {
        private String name;
        private int rollNo;
        @JsonSerialize(using = CustomDateSerializer.class)
        private Date dateOfBirth;

        public Student(String name, int rollNo, Date dob){
            this.name = name;
            this.rollNo = rollNo;
            this.dateOfBirth = dob;
        }
        public String getName(){
            return name;
        }
        public int getRollNo(){
            return rollNo;
        }
        public Date getDateOfBirth(){
            return dateOfBirth;
        }
    }

    static class CustomDateSerializer extends StdSerializer<Date> {
        private static final long serialVersionUID = 1L;
        private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        public CustomDateSerializer() {
            this(null);
        }
        public CustomDateSerializer(Class<Date> t) {
            super(t);
        }
        @Override
        public void serialize(Date value, JsonGenerator generator, SerializerProvider arg2) throws IOException {
            generator.writeString(formatter.format(value));
        }
    }*/
}
