package me.bigmonkey.backjoon;

import java.util.Stack;

public class bac2504 {

    static String input = "(()[[]])([])";  //정답 28

    public static void main(String[] args) {
        int ans = 0;
        int tmp = 1;
        boolean fail = false;
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);

            switch (cur) {
                case '(':
                    stack.push(cur);
                    tmp *= 2;
                    break;

                case '[':
                    stack.push(cur);
                    tmp *= 3;
                    break;

                case ')':
                    if(!stack.isEmpty()) {
                        Character pop = stack.pop();
                        if(pop == '(') {
                            if(input.charAt(i-1) == '(') {
                                ans += tmp;
                            }
                            tmp /= 2;
                        }
                        else {
                            System.out.println(0);
                            return;
                        }
                    } else {
                        System.out.println(0);
                        return;
                    }

                    break;

                case ']':
                    if(!stack.isEmpty()) {
                        Character pop = stack.pop();
                        if(pop == '[') {
                            if(input.charAt(i-1) == '[') {
                                ans += tmp;
                            }
                            tmp /= 3;

                        } else {
                            System.out.println(0);
                            return;
                        }
                    } else {
                        System.out.println(0);
                        return;
                    }
                    break;

                default:
                    System.out.println(0);
                    return;
            }

        }

        if(!stack.isEmpty()) {
            System.out.println(0);
            return;
        }

        System.out.println(ans);
    }
}
