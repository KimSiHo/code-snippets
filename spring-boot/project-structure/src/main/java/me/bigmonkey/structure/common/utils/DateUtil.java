package me.bigmonkey.structure.common.utils;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static int getAmericanAge(String birthDate) {
//        LocalDate now = LocalDate.now();
//        LocalDate parsedBirthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate();
        LocalDate parsedBirthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        int americanAge = now.minusYears(parsedBirthDate.getYear()).getYear(); // (1)

        // (2)
        // 생일이 지났는지 여부를 판단하기 위해 (1)을 입력받은 생년월일의 연도에 더한다.
        // 연도가 같아짐으로 생년월일만 판단할 수 있다!
        if (parsedBirthDate.plusYears(americanAge).isAfter(now)) {
            americanAge = americanAge -1;
        }

        return americanAge;
    }

    public static int getParentChildAge(String parentBirth, String childBirth) {
        LocalDate parentBirthDate = LocalDate.parse(parentBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate childBirthDate = LocalDate.parse(childBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return childBirthDate.minusYears(parentBirthDate.getYear()).getYear();
    }

    public static String convertDateFormat(String date, String beforeFormat, String afterFormat) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(beforeFormat));

        return localDate.format(DateTimeFormatter.ofPattern(afterFormat));
    }

    public static boolean checkLegal(int age) {
        return age >= 14;
    }

    public static boolean checkParentAge(String parentBirth, String childBirth) {
        return DateUtil.getParentChildAge(parentBirth, childBirth) <= 18;
    }
}
