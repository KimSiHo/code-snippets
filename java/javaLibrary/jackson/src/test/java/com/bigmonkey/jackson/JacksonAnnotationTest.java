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
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
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
import com.fasterxml.jackson.annotation.JsonValue;
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
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonAnnotationTest {

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
    }*/


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

        @JsonGetter
        public String getName(){
            return name;
        }

        *//*public String getStudentName(){
            return name;
        }*//*

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


    //
    /*@Test
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
    @Test
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
    }
}
