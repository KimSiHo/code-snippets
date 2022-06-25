package me.bigmonkey.java.jcf.list;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class JavaList {

    public static void main(String[] args) {
        JavaList javaList = new JavaList();
        javaList.test1();
        javaList.test2();
        javaList.test3();
    }

    // inital capacity(ic)를 지정해도 size는 변함이 없는 것이, ic는 내부 배열의 길이 값이고
    // size가 노출하는 것은 현재 들고 있는 원소의 갯수이다, size의 반환 값은 항상 현재 capacity보다 적은 값이다
    // size가 capacity를 초과하면 내부적으로 배열 사이즈 (capacity)를 증가시키는 것이다
    private void test1() {
        ArrayList<Integer> test = new ArrayList<>();
        List<Integer> test1 = new ArrayList<>(100);
        System.out.println(test.size());
        System.out.println(test1.size());

        System.out.println("============= test1 =============");
    }

    // 리스트를 특정 유형의 배열로 변환, 크기가 0인 String 배열을 전달하더라도 배열에는 List 의 모든 요소가 포함
    private void test2() {
        List<String> list = new ArrayList<>();
        list.add("element 1");
        list.add("element 2");
        list.add("element 3");
        list.add("element 4");

        String[] stringArr = list.toArray(new String[0]);
        System.out.println(stringArr.length);
        for (int i = 0; i < stringArr.length; i++) {
            System.out.println(i + ", " + stringArr[i]);
        }

        System.out.println("============= test2 =============");
    }

    private void test3() {

    }
}
