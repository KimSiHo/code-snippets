package me.bigmonkey.backjoon;

import java.util.Arrays;
import java.util.Comparator;

public class bac1922 {

    static int n = 6;
    static int m = 9;
    static int[][] inputs = {{1, 2, 5}, {1, 3, 4}, {2, 3, 2}, {2, 4, 7}, {3, 4, 6}, {3, 5, 11}, {4, 5, 3}, {4, 6, 8}, {5, 6, 8}};

    static int ans = 0;
    static int[] arr = new int[n + 1];

    public static void main(String[] args) {

        for (int i = 1; i <= n; i++) {
            arr[i] = i;
        }

        Arrays.sort(inputs, (t1, t2) -> Integer.compare(t1[2], t2[2]));

        for (int i = 0; i < inputs.length; i++) {
            int[] temp = inputs[i];
            if(union(temp[0], temp[1])){
               ans += temp[2];
            }
        }

        System.out.println(ans);
    }

    private static boolean union(int a, int b) {
        int a1 = find(arr[a]);
        int b1 = find(arr[b]);

        if(a1 != b1) {
            arr[a1] = arr[b1];
            return true;
        } else {
            return false;
        }
    }

    private static int find(int input) {
        if(arr[input] == input) return input;
        else return arr[input] = find(arr[input]);
    }
}
