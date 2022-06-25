package me.bigmonkey.java.jcf.array;

// 지역으로 배열 변수 선언해도 0으로 초기화 된다

public class JavaArray {

    public static void main(String[] args) {
        int[] test = new int[3];

        for (int i = 0; i < test.length; i++) {
            System.out.println(i + ", " + test[i]);
        }

        JavaArray javaArray = new JavaArray();
        javaArray.test1();
    }

    private void test1() {
        int[] test = new int[3];

        for (int i = 0; i < test.length; i++) {
            System.out.println(i + ", " + test[i]);
        }
    }
}
