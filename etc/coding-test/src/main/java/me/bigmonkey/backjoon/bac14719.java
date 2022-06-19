package me.bigmonkey.backjoon;


public class bac14719 {

/*    static int h = 4;
    static int w = 4;
    static int[] arr = {3, 0, 1, 4}; // 정답 5*/

    /*static int h = 4;
    static int w = 8;
    static int[] arr = {3, 1, 2, 3, 4, 1, 1, 2}; // 정답 5*/

    static int h = 3;
    static int w = 5;
    static int[] arr = {0, 0, 0, 2, 0}; // 정답 0

    public static void main(String[] args) {
        int sum = 0;

        for (int i = 1; i < arr.length - 1; i++) {
            int leftMax = Integer.MIN_VALUE;
            int rightMax = Integer.MIN_VALUE;

            for (int j = i-1; j >= 0; j--) {
                leftMax = Math.max(leftMax, arr[j]);
            }

            for (int j = i+1; j < arr.length; j++) {
                rightMax = Math.max(rightMax, arr[j]);
            }

            int height = Math.min(leftMax, rightMax);
            if(height > arr[i]) sum += height-arr[i];
        }

        System.out.println(sum);
    }

}
