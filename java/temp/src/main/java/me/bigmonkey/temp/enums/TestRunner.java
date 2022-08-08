package me.bigmonkey.temp.enums;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // static 메소드, 문자열에 해당하는 enum 값이 없을 경우 예외 발생
        DevType devType = Enum.valueOf(DevType.class, "SERVER");
        DevType devType1 = DevType.valueOf("MOBILE");

        System.out.println(devType.getDesc());
        System.out.println(devType1.getDesc());

        final DevType[] values = DevType.values();
        for (DevType value : values) {
            System.out.println("=========");
            System.out.println(value.getDesc());
        }

        // non static 메소드
        DevType devType2 = DevType.valueOf("MOBILE");
        DevType devType3 = DevType.valueOf("SERVER");
        //  호출된 값의 이름을 String으로 리턴.
        final String name = devType2.name();
        System.out.println("name = " + name);
        System.out.println(devType2);
        //  해당 값이 enum에 정의된 순서를 리턴.
        System.out.println(devType2.ordinal());
        //  enum과 지정된 객체의 순서를 비교. 지정된 객체보다 작은 경우 음의 정수, 동일하면 0, 크면 양의 정수 리턴
        System.out.println(devType2.compareTo(devType3));

    }
}
