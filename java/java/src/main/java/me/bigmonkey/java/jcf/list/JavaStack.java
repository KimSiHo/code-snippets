package me.bigmonkey.java.jcf.list;

import java.util.Stack;

public class JavaStack {

    public static void main(String[] args) {
        JavaStack javaStack = new JavaStack();

        javaStack.test1();
    }

    // stack 기본 메소드 테스트
    private void test1() {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        stack.clear();


        stack.push(1);
        stack.push(2);
        stack.push(3);
        if (!stack.empty()) {
            if (stack.peek() == 3) {
                stack.pop();
            }
        }

        if (stack.search(3) == -1) {
            System.out.println("3 is poped");
        }

        // search 메소드가 반환하는 값은 인덱스가 아니라 순번이다!
        System.out.println(stack.search(1));
        System.out.println(stack.search(2));

        System.out.println("============= test1 =============");
    }
}
