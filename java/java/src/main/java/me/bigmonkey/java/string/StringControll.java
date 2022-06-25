package me.bigmonkey.java.string;

import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.util.ArrayUtils;

public class StringControll {

    public static void main(String[] args) {
        StringControll stringControll = new StringControll();

        stringControll.test1();
        stringControll.test2();
        stringControll.test3();
        stringControll.test4();

    }

    // 문자열을 문자 배열로 복사
    private void test1() {
        String str = "abcde";
        char[] ch = new char[4];

        str.getChars(0, 2, ch, 1);
        for (int i = 0; i < ch.length; i++) {
            System.out.println(ch[i]);
        }

        System.out.println("============= test1 =============");
    }

    // 문자열 문자 배열로 변환
    private void test2() {
        String str = "abcde";
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i]);
        }

        System.out.println("============= test2 =============");
    }

    // 공백 split > 공백 연속 2개
    private void test3() {
        String str = "aa bb   cc dd e ";
        String[] strings = str.split(" ");

        for (String string : strings) {
            System.out.println(string);
            System.out.println(string.length());
        }

        System.out.println("============= test3 =============");
    }

    // 그냥 기본 comparator로 정렬하는 경우, 앞 자리 부터 숫자순으로 된다!
    private void test4() {
        String a = "0000";
        String b = "1000";
        String c = "0100";
        String d = "0010";
        String e = "0200";
        String f = "0003";

        List<String> list = List.of(a, b, c, d, e, f);
        String[] strings = list.toArray(new String[0]);
        Arrays.sort(strings);

        System.out.println(Arrays.toString(strings));
        System.out.println("============= test4 =============");
    }

}
