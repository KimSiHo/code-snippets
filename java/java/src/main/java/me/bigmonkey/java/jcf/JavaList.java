package me.bigmonkey.java.jcf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class JavaList {

    public static void main(String[] args) {
        JavaList javaList = new JavaList();
        /*javaList.test1();
        javaList.test2();
        javaList.test3();*/
        javaList.test4();
    }

    private void test1() {
        ArrayList<Integer> test = new ArrayList<>();
        List<Integer> test1 = new ArrayList<>(10);
        System.out.println(test.size());
        System.out.println(test1.size());

        for (int i = 0; i < test1.size(); i++) {
            System.out.println(i + ", " + test1.get(i));
        }
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
    }

    private void test3() {
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new LinkedList<>();

        linkedList.add(1);
        linkedList.add(111);
        System.out.println(linkedList);
    }

    private void test4() {
        Stack<Integer> stack = new Stack<>();

        if(stack.isEmpty()) {
            stack.push(1);
            stack.push(2);
            stack.push(3);
        }

        if(!stack.empty()){
            if(stack.peek() == 3)
                stack.pop();

        }

        if(stack.search(3) == -1){
            System.out.println("3 is poped");
        }

        System.out.println(stack.search(1));
        System.out.println(stack.search(2));
    }
}
