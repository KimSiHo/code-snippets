package me.bigmonkey.backjoon;

public class bac1806 {

    static int n = 10;
    static int s = 15;
    static int[] inputs = {5, 1, 3, 5, 10, 7, 4, 9, 2, 8};

    public static void main(String[] args) {

        int start = 0;
        int end = 0;
        int res = Integer.MAX_VALUE;
        int sum = 0;

        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i];
            end++;
            if(sum < s) continue;

            while(sum - inputs[start] >= s) {
                sum -= inputs[start];
                start++;
            }

            if(end - start < res) res = end - start;
        }
        if (res == Integer.MAX_VALUE) {
            System.out.println(0);
            return;
        }
        System.out.println(res);
    }

}
